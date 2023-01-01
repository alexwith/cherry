package net.cherry.codec;

import org.bson.BsonString;
import org.bson.BsonValue;

public class StringCodec implements MongoCodec<String> {

    @Override
    public BsonValue encode(String toEncode) {
        return new BsonString(toEncode);
    }

    @Override
    public String decode(BsonValue toDecode) {
        return toDecode.asString().getValue();
    }

    @Override
    public boolean isValid(Object object) {
        return object instanceof String;
    }
}
