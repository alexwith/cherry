package net.cherry.proxy.interceptor;

import java.lang.reflect.Method;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityStorage;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;
import net.cherry.util.SneakyThrows;

public class SetterInterceptor implements Interceptor {

    @Override
    public Builder<?> create(Builder<?> builder) {
        return builder
            .method(ElementMatchers.isSetter())
            .intercept(MethodDelegation.to(SetterInterceptor.class));
    }

    @RuntimeType
    public static void intercept(@This Entity<?> entity, @Origin Method method, @AllArguments Object[] args) {
        final EntityController<?> controller = entity.getController();
        final ProxiedClass<?> proxiedClass = controller.getProxiedClass();
        final ProxyField field = proxiedClass.getField(method);
        if (field == null) {
            SneakyThrows.supply(() -> method.invoke(entity));
            return;
        }

        final Object value = args[0];

        final EntityStorage storage = controller.getStorage();
        storage.set(field.getPath(), value);
    }
}
