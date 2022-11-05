package test;

import java.util.HashMap;
import net.cherry.Cherry;
import net.cherry.enums.SortingOrder;

public class CherryTest {

    public static void main(String[] args) {
        final TestEntity foundTest = Cherry.findMany(TestEntity.class, (query) -> query
            .where("age", (options) -> options
                .sortBy(SortingOrder.ASCENDING)
            )
            .where("name", (options) -> options
                .endsWith("x")
            )
        );

        final TestEntity hello = Cherry.create(TestEntity.class);
        hello.setAccounts(new HashMap<>());

        hello.getAccounts().put("bob", 100);

        System.out.println("oke: " + hello.getAccounts().get("bob"));
    }
}
