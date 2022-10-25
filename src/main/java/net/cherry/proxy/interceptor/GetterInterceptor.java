package net.cherry.proxy.interceptor;

import java.lang.reflect.Method;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;
import net.cherry.util.SneakyThrows;

public class GetterInterceptor implements Interceptor {

    @Override
    public Builder<?> create(Builder<?> builder) {
        return builder
            .method(ElementMatchers.isGetter())
            .intercept(MethodDelegation.to(GetterInterceptor.class));
    }

    @RuntimeType
    public static Object intercept(@This Object entity, @Origin Method method) {
        final EntityController<?> controller = EntityControllerManager.getController(entity);
        final ProxiedClass<?> proxiedClass = controller.getProxiedClass();
        final ProxyField field = proxiedClass.getField(method);
        if (field == null) {
            return SneakyThrows.supply(() -> method.invoke(entity));
        }

        return null;
    }
}
