package net.cherry.test;

import java.lang.reflect.Type;
import java.util.UUID;
import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import net.cherry.codec.MongoCodec;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.junit.jupiter.api.BeforeEach;

public class MongoEntityTest {

    @BeforeEach
    void setUp() {
        Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "test"));

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
