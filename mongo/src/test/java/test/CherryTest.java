package test;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.UUID;
import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import net.cherry.codec.MongoCodec;
import net.cherry.query.Query;
import org.bson.BsonString;
import org.bson.BsonValue;

public class CherryTest {

    public static void main(String[] args) {
        Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "test"));

        registerCodecs();

        /*final TestEntity entity = Cherry.createWithId(TestEntity.class, UUID.randomUUID());

        entity.setAccounts(new HashMap<>());
        entity.getAccounts().put("bob", 100);
        entity.getAccounts().put("bobby", 40);

        entity.setAge(20);

        Cherry.client().save(entity);*/

        final Collection<TestEntity> entities = Cherry.findMany(TestEntity.class, Query.all());
        for (final TestEntity entity : entities) {
            System.out.println("Entity: " + entity.getId());

            for (final Entry<String, Integer> entry : entity.getAccounts().entrySet()) {
                System.out.println("  entry: " + entry.getKey() + " -> " + entry.getValue());
            }
        }
    }

    private static void registerCodecs() {
        Cherry.codecRegistry().register(new MongoCodec<UUID>() {

            @Override
            public boolean isValid(Class<?> clazz) {
                return UUID.class.isAssignableFrom(clazz);
            }

            @Override
            public UUID decode(BsonValue toDecode, Type[] typeArgs) {
                return UUID.fromString(toDecode.asString().getValue());
            }

            @Override
            public BsonValue encode(UUID toEncode) {
                return new BsonString(toEncode.toString());
            }
        });
    }
}