package net.cherry.codec;

import java.lang.reflect.Type;
import org.bson.BsonString;
import org.bson.BsonValue;

public class StringCodec implements MongoCodec<String> {

    @Override
    public BsonValue encode(String toEncode) {
        return new BsonString(toEncode);
    }

    @Override
    public String decode(BsonValue toDecode, Type[] typeArgs) {
        return toDecode.asString().getValue();
    }

    @Override
    public boolean isValid(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }
}
