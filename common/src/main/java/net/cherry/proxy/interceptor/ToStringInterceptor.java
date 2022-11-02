package net.cherry.proxy.interceptor;

import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;

public class ToStringInterceptor implements Interceptor {

    @Override
    public Builder<?> create(Builder<?> builder) {
        return builder
            .method(ElementMatchers.isToString())
            .intercept(MethodDelegation.to(ToStringInterceptor.class));
    }

    @RuntimeType
    public static Object intercept(@This Object entity) {
        final EntityController<?> controller = EntityControllerManager.getController(entity);

        return controller.getDetailedName();
    }
}
