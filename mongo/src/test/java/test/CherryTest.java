package test;

import java.util.HashMap;
import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import net.cherry.proxy.ProxyFactory;
import net.cherry.proxy.entity.ProxiedClass;

public class CherryTest {

    public static void main(String[] args) {
        Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "test"));

        final TestEntity entity = Cherry.create(TestEntity.class);
        entity.setAccounts(new HashMap<>());
        entity.getAccounts().put("bob", 100);
        entity.getAccounts().put("bobby", 40);

        entity.setAge(10);

        Cherry.client().save(entity);

        /*System.out.println("oke: " + entity.getAccounts().get("bob"));

        final Collection<TestEntity> entities = Cherry.findMany(TestEntity.class, Query::all);*/

        /*.where("age", (options) -> options
                .sortBy(SortingOrder.ASCENDING)
            )
            .where("name", (options) -> options
                .endsWith("x")
            )*/
    }
}
