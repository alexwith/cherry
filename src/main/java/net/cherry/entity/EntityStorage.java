package net.cherry.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityStorage {
    private final Map<String, Object> values = new ConcurrentHashMap<>();

    public Object get(String field) {
        return this.values.get(field);
    }

    public void set(String field, Object object) {
        this.values.put(field, object);
    }
}
