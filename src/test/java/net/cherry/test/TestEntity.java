package net.cherry.test;

import java.util.UUID;
import net.cherry.annotation.Entity;

@Entity(database = "test")
public class TestEntity {
    private final UUID id;
    private final String name;
    private final int age;

    public TestEntity(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}
