package net.cherry.codec;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.cherry.Cherry;
import net.cherry.CherryMongoClient;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;

public class MapCodec implements MongoCodec<Map<?, ?>> {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map<?, ?> decode(BsonValue toDecode, Type[] typeArgs) {
        final BsonArray bsonMap = toDecode.asArray();
        final Map map = new HashMap<>();

        final CodecRegistry codecRegistry = Cherry.codecRegistry();
        for (final BsonValue entry : bsonMap) {
            final BsonDocument bsonEntry = (BsonDocument) entry;
            final BsonValue keyBson = bsonEntry.get("K");
            final BsonValue valueBson = bsonEntry.get("V");

            final Codec keyCodec = codecRegistry.lookup(typeArgs[0]);
            final Codec valueCodec = codecRegistry.lookup(typeArgs[1]);

            final Object key = keyCodec.decode(keyBson, typeArgs);
            final Object value = valueCodec.decode(valueBson, typeArgs);
            map.put(key, value);
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
    public boolean isValid(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
}
