package test;

import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import net.cherry.query.Query;

public class CherryTest {

    public static void main(String[] args) {
        Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "test"));

        /*final TestEntity entity = Cherry.createWithId(TestEntity.class, UUID.randomUUID());

        entity.setAccounts(new HashMap<>());
        entity.getAccounts().put("bob", 100);
        entity.getAccounts().put("bobby", 40);

        entity.setAge(20);

        Cherry.client().save(entity);*/

        for (final TestEntity entity : Cherry.findMany(TestEntity.class, Query.all())) {
            System.out.println("ayo: " + entity.getId() + " -> " + entity.getAge());
        }

        //Query.and(Query.equals("bob", 5), Query.lessThan("bobby", 10));
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
