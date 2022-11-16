package net.cherry.entity;

import java.lang.reflect.Field;
import net.cherry.annotation.Entity;
import net.cherry.annotation.EntityId;

public class EntityMetadata {
    private String database;
    private String idField;

    public EntityMetadata(Class<?> originalClass) {
        this.retrieveMetadata(originalClass);
    }

    public String getDatabase() {
        return this.database;
    }

    public String getIdField() {
        return this.idField;
    }

    private void retrieveMetadata(Class<?> originalClass) {
        this.database = originalClass.getAnnotation(Entity.class).database();

        for (final Field field : originalClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(EntityId.class)) {
                continue;
            }

            this.idField = field.getName();
            break;
        }
    }
}
