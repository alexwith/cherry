package test;

import java.util.UUID;
import net.cherry.annotation.Entity;
import net.cherry.annotation.EntityDefaults;

@Entity(database = "test")
public class TestEntity {
    private final UUID id;

    private String name;
    private int age;

    @EntityDefaults
    private static final TestEntity DEFAULT = new TestEntity(
        null,
        "Anonymous",
        0
    );

    protected TestEntity(UUID id, String name, int age) {
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

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}