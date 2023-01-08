package net.cherry.proxy.entity;

import java.lang.reflect.Field;
import net.cherry.annotation.EntityId;

public class ProxyMetadata {
    private String idField;

    public ProxyMetadata(Class<?> originalClass) {
        this.retrieveMetadata(originalClass);
    }

    public String getIdField() {
        return this.idField;
    }

    private void retrieveMetadata(Class<?> originalClass) {
        for (final Field field : originalClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(EntityId.class)) {
                continue;
            }

            this.idField = field.getName();
            break;
        }
    }
}
