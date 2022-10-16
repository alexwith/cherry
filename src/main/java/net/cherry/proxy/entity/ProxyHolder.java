package net.cherry.proxy.entity;

import java.lang.reflect.Constructor;

public class ProxyHolder<T> {
    private final Class<T> proxiedClass;
    private final Constructor<T> constructor;

    @SuppressWarnings("unchecked")
    public ProxyHolder(Class<T> proxiedClass) {
        this.proxiedClass = proxiedClass;
        this.constructor = (Constructor<T>) proxiedClass.getDeclaredConstructors()[0];
    }

    public Class<T> getProxiedClass() {
        return this.proxiedClass;
    }

    public Constructor<T> getConstructor() {
        return this.constructor;
    }
}
