package net.cherry.config;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.cherry.codec.Codec;
import net.cherry.codec.MapCodec;
import net.cherry.codec.MongoCodec;
import net.cherry.codec.PrimitiveCodec;
import net.cherry.codec.StringCodec;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;

public interface CodecConfig {

    Collection<Codec<?, ?>> MONGO_CODECS = List.of(
        new PrimitiveCodec<Byte>(
            BsonInt32::new,
            (value) -> (byte) value.asInt32().getValue(),
            byte.class,
            Byte.class
        ),
        new PrimitiveCodec<Short>(
            BsonInt32::new,
            (value) -> (short) value.asInt32().getValue(),
            short.class,
            Short.class
        ),
        new PrimitiveCodec<>(
            BsonInt32::new,
            (value) -> value.asInt32().getValue(),
            int.class,
            Integer.class
        ),
        new PrimitiveCodec<>(
            BsonInt64::new, (value) -> value.asInt64().getValue(),
            long.class,
            Long.class
        ),
        new PrimitiveCodec<Float>(
            BsonDouble::new,
            (value) -> (float) value.asDouble().getValue(),
            float.class,
            Float.class
        ),
        new PrimitiveCodec<>(
            BsonDouble::new,
            (value) -> value.asDouble().getValue(),
            double.class,
            Double.class
        ),
        new PrimitiveCodec<>(
            (character) -> new BsonString(String.valueOf(character)),
            (value) -> value.asString().getValue().charAt(0),
            char.class,
            Character.class
        ),
        new PrimitiveCodec<>(
            BsonInt32::new,
            (value) -> value.asInt32().getValue(),
            int.class,
            Integer.class
        ),
        new StringCodec(),
        new MapCodec(),
        new MongoCodec<UUID>() {

            @Override
            public boolean isValid(Class<?> clazz) {
                return UUID.class.isAssignableFrom(clazz);
            }

            @Override
            public UUID decode(BsonValue toDecode) {
                return UUID.fromString(toDecode.asString().getValue());
            }

            @Override
            public BsonValue encode(UUID toEncode) {
                return new BsonString(toEncode.toString());
            }
        }
    );
}
