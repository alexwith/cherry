package net.cherry.proxy.interceptor;

import net.bytebuddy.dynamic.DynamicType.Builder;

public interface Interceptor {

    Builder<?> create(Builder<?> builder);
}
