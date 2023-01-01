package net.cherry.entity;

public class EntitySettings<T> {
    private String database;
    private T defaultEntity;

    public String getDatabase() {
        return this.database;
    }

    public EntitySettings<T> database(String database) {
        this.database = database;
        return this;
    }

    public T getDefaultEntity() {
        return this.defaultEntity;
    }

    public EntitySettings<T> defaults(T defaults) {
        this.defaultEntity = defaults;
        return this;
    }
}
