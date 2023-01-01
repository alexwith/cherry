package net.cherry.codec;

import java.util.Set;
import org.bson.BsonValue;

public class PrimitiveCodec<T> implements MongoCodec<T> {
    private final Encoder<T, BsonValue> encoder;
    private final Decoder<T, BsonValue> decoder;
    private final Set<Class<?>> types;

    public PrimitiveCodec(Encoder<T, BsonValue> encoder, Decoder<T, BsonValue> decoder, Class<?>... types) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.types = Set.of(types);
    }

    @Override
    public BsonValue encode(T toEncode) {
        return this.encoder.encode(toEncode);
    }

    @Override
    public T decode(BsonValue toDecode) {
        return this.decoder.decode(toDecode);
    }

    @Override
    public boolean isValid(Object object) {
        return this.types.contains(object.getClass());
    }
}
