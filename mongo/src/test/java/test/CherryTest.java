package test;

import java.util.Collection;
import java.util.HashMap;
import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import net.cherry.query.Query;

public class CherryTest {

    public static void main(String[] args) {
        Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "test"));

        final TestEntity hello = Cherry.create(TestEntity.class);
        hello.setAccounts(new HashMap<>());

        hello.getAccounts().put("bob", 100);

        System.out.println("oke: " + hello.getAccounts().get("bob"));

        final Collection<TestEntity> entities = Cherry.findMany(TestEntity.class, Query::all);

        /*.where("age", (options) -> options
                .sortBy(SortingOrder.ASCENDING)
            )
            .where("name", (options) -> options
                .endsWith("x")
            )*/
    }
}
