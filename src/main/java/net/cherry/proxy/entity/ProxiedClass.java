package net.cherry.proxy.entity;

import java.lang.reflect.Constructor;

public class ProxiedClass<T> {
    private final Class<T> clazz;
    private final Class<T> originClass;
    private final Constructor<T> constructor;

    @SuppressWarnings("unchecked")
    public ProxiedClass(Class<T> clazz, Class<T> originClass) {
        this.clazz = clazz;
        this.originClass = originClass;
        this.constructor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    public Class<T> getOriginClass() {
        return this.originClass;
    }

    public Constructor<T> getConstructor() {
        return this.constructor;
    }
}
