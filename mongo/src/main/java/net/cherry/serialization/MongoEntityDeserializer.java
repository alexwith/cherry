package net.cherry.serialization;

import net.cherry.Cherry;
import net.cherry.codec.Codec;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityStorage;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;
import net.cherry.proxy.entity.ProxyMetadata;
import net.cherry.type.FieldType;
import org.bson.BsonDocument;
import org.bson.BsonValue;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MongoEntityDeserializer implements EntityDeserializer<BsonDocument> {

    @Override
    public <U extends Entity<U>> U deserialize(Class<U> identifier, BsonDocument serializedEntity) {
        final U entity = Cherry.createWithId(identifier, null);
        final EntityController<U> controller = entity.getController();
        final EntityStorage storage = controller.getStorage();
        final ProxiedClass<U> proxiedClass = controller.getProxiedClass();
        final ProxyMetadata metadata = proxiedClass.getMetadata();

        for (final ProxyField field : proxiedClass.getFields().values()) {
            final String path = field.getPath();
            final FieldType type = field.getType();
            final boolean isIdField = metadata.getIdField().equals(path);

            final BsonValue value = serializedEntity.get(isIdField ? "_id" : path);
            if (value == null || value.isNull()) {
                continue;
            }

            final Codec codec = this.getCodec(type);
            storage.set(path, codec.decode(value));
        }

        return entity;
    }

    private Codec getCodec(FieldType type) {
        for (final Codec codec : Cherry.client().provideCodecs()) {
            if (!codec.isValid(type.getType())) {
                continue;
            }

            return codec;
        }

        return null;
    }
}
