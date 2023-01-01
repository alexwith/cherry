package net.cherry.entity;

import java.lang.reflect.Field;
import net.cherry.annotation.EntityId;

public class EntityMetadata {
    private String idField;

    public EntityMetadata(Class<?> originalClass) {
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
