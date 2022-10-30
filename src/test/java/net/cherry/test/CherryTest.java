package net.cherry.test;

import net.cherry.client.CherryClient;
import net.cherry.enums.SortingOrder;

public class CherryTest {

    public static void main(String[] args) {
        final TestEntity foundTest = CherryClient.findMany(TestEntity.class, (query) -> query
            .where("age", (options) -> options
                .sortBy(SortingOrder.ASCENDING)
            )
            .where("name", (options) -> options
                .endsWith("x")
            )
        );

        final TestEntity hello = CherryClient.create(TestEntity.class);
        System.out.println("yo: " + hello.getId() + " -> " + hello.getName());

        hello.setName("bob");

        System.out.println("oke: " + hello.getName());
    }
}
