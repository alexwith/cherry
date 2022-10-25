package net.cherry.proxy.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxyField {
    private final Field field;
    private final Method method;

    public ProxyField(Field field, Method method) {
        this.field = field;
        this.method = method;
    }

    public Field getField() {
        return this.field;
    }

    public Method getMethod() {
        return this.method;
    }
}
