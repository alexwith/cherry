package net.cherry.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.cherry.CherryMongoClient;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;

public class MapCodec implements MongoCodec<Map<?, ?>> {

    @Override
    public Map<?, ?> decode(BsonValue toDecode) {
        final BsonArray bsonMap = toDecode.asArray();
        final Map<?, ?> map = new HashMap<>();

        for (final BsonValue entry : bsonMap) {
            final BsonDocument bsonEntry = (BsonDocument) entry;
            final BsonValue keyBson = bsonEntry.get("K");
            final BsonValue valueBson = bsonEntry.get("V");
        }

        return map;
    }

    @Override
    public BsonValue encode(Map<?, ?> toEncode) {
        final BsonArray bsonMap = new BsonArray(toEncode.size());

        for (final Entry<?, ?> entry : toEncode.entrySet()) {
            final Object key = entry.getKey();
            final Object value = entry.getValue();

            final BsonValue keyBson = CherryMongoClient.SERIALIZER.serializeValue(key);
            final BsonValue valueBson = CherryMongoClient.SERIALIZER.serializeValue(value);

            final BsonDocument bsonEntry = new BsonDocument(2);
            bsonEntry.put("K", keyBson);
            bsonEntry.put("V", valueBson);

            bsonMap.add(bsonEntry);
        }

        return bsonMap;
    }

    @Override
    public boolean isValid(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
