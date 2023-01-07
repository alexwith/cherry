package net.cherry.proxy.entity;

import java.lang.reflect.Field;
import net.cherry.type.FieldType;

public class ProxyField {
    private final Field field;
    private final String path;
    private final FieldType type;

    public ProxyField(Field field) {
        this.field = field;
        this.path = field.getName();
        this.type = new FieldType(field.getType());
    }

    public Field getField() {
        return this.field;
    }

    public String getPath() {
        return this.path;
    }

    public FieldType getType() {
        return this.type;
    }
}
