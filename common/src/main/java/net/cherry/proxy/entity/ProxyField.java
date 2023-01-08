package net.cherry.proxy.entity;

import java.lang.reflect.Field;
import net.cherry.type.TypeHolder;

public class ProxyField {
    private final Field field;
    private final String path;
    private final TypeHolder type;

    public ProxyField(Field field) {
        this.field = field;
        this.path = field.getName();
        this.type = new TypeHolder(field);
    }

    public Field getField() {
        return this.field;
    }

    public String getPath() {
        return this.path;
    }

    public TypeHolder getType() {
        return this.type;
    }
}
