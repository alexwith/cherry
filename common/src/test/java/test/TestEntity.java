package test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.cherry.annotation.Entity;
import net.cherry.annotation.EntityDefaults;

@Entity(database = "test")
public class TestEntity {
    private final UUID id;

    private String name;
    private int age;
    private Map<String, Integer> accounts;

    @EntityDefaults
    private static final TestEntity DEFAULT = new TestEntity(
        null,
        "Anonymous",
        0,
        new HashMap<>()
    );

    protected TestEntity(UUID id, String name, int age, Map<String, Integer> accounts) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.accounts = accounts;
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
