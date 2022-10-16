package net.cherry.test;

import net.cherry.client.CherryClient;
import net.cherry.enums.SortingOrder;

public class CherryTest {

    public static void main(String[] args) {
        final TestEntity test = new TestEntity();

        final TestEntity foundTest = CherryClient.findMany(TestEntity.class, (query) -> query
            .where("age", (options) -> options
                .sortBy(SortingOrder.ASCENDING)
            )
            .where("name", (options) -> options
                .endsWith("x")
            )
        );
    }
}
