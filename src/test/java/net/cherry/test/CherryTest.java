package net.cherry.test;

import net.cherry.entity.EntityClient;

public class CherryTest {

    public static void main(String[] args) {
        final TestEntity test = new TestEntity();

        final TestEntity foundTest = EntityClient.findMany(TestEntity.class, (query) -> query
            .where("bob", null)
        );
    }
}
