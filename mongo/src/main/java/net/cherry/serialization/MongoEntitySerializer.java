package net.cherry.serialization;

import java.util.Map.Entry;
import net.cherry.Cherry;
import net.cherry.codec.Codec;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityMetadata;
import net.cherry.entity.EntityStorage;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonValue;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MongoEntitySerializer implements EntitySerializer<BsonDocument> {

    @Override
    public <T extends Entity> BsonDocument serialize(T entity) {
        final EntityController controller = entity.getController();
        final EntityMetadata metadata = controller.getMetadata();
        final EntityStorage storage = controller.getStorage();
        
        final BsonDocument document = new BsonDocument();
        for (final Entry<String, Object> field : storage.getValues().entrySet()) {
            final String path = field.getKey();
            final BsonValue serializedValue = this.serializeValue(field.getValue());
            if (metadata.getIdField().equals(path)) {
                document.put("_id", serializedValue);
                continue;
            }

            document.put(path, serializedValue);
        }

        return document;
    }

    public BsonValue serializeValue(Object value) {
        if (value instanceof final Entity<?> entity) {
            return this.serialize(entity);
        }

        return this.encode(value);
    }

    private BsonValue encode(Object value) {
        for (final Codec codec : Cherry.client().provideCodecs()) {
            if (!codec.isValid(value.getClass())) {
                continue;
            }

            return (BsonValue) codec.encode(value);
        }

        return new BsonNull();
    }
}
