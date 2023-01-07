package test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.UnaryOperator;
import net.cherry.annotation.EntityId;
import net.cherry.entity.Entity;
import net.cherry.entity.EntitySettings;

public class TestEntity implements Entity<TestEntity> {

    @EntityId
    private final UUID id;

    private String name;
    private int age;
    private Map<String, Integer> accounts;

    protected TestEntity(UUID id, String name, int age, Map<String, Integer> accounts) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.accounts = accounts;
    }

    @Override
    public UnaryOperator<EntitySettings<TestEntity>> settings() {
        return (settings) -> settings
            .database("test")
            .defaults(new TestEntity(
                null,
                "Anonymous",
                0,
                new HashMap<>()
            ));
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

    public Map<String, Integer> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Map<String, Integer> accounts) {
        this.accounts = accounts;
    }
}
