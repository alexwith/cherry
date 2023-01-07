package net.cherry.type;

public class FieldType {
    private final Class<?>[] types;

    public FieldType(Class<?>... types) {
        this.types = types;
    }

    public Class<?> getType() {
        return this.types[0];
    }
}
