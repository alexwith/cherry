package net.cherry.test.entity;

import java.util.List;
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
    private List<String> todo;
    private Map<String, Integer> items;

    protected TestEntity(UUID id, String name, int age, List<String> todo, Map<String, Integer> items) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.todo = todo;
        this.items = items;
    }

    @Override
    public UnaryOperator<EntitySettings<TestEntity>> settings() {
        return (settings) -> settings
            .database("test")
            .defaults(new TestEntity(
                null,
                "Bob",
                18,
                List.of(
                    "Do the dishes",
                    "Make dinner"
                ),
                Map.of(
                    "Stones", 4
                )
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

    public List<String> getTodo() {
        return this.todo;
    }

    public void setTodo(List<String> todo) {
        this.todo = todo;
    }

    public Map<String, Integer> getItems() {
        return this.items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
}
