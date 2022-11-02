package net.cherry.proxy.entity;

import java.lang.reflect.Field;

public class ProxyField {
    private final Field field;
    private final String path;

    public ProxyField(Field field) {
        this.field = field;
        this.path = field.getName();
    }

    public Field getField() {
        return this.field;
    }

    public String getPath() {
        return this.path;
    }
}
