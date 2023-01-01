package net.cherry.serialization;

import java.util.Map.Entry;
import net.cherry.Cherry;
import net.cherry.codec.Codec;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.entity.EntityStorage;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonValue;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MongoEntitySerializer implements EntitySerializer<BsonDocument> {

    @Override
    public <T extends Entity> BsonDocument serialize(T entity) {
        final EntityController controller = EntityControllerManager.getController(entity);
        final EntityStorage storage = controller.getStorage();

        final BsonDocument document = new BsonDocument();
        for (final Entry<String, Object> field : storage.getValues().entrySet()) {
            document.put(field.getKey(), this.serializeValue(field.getValue()));
        }

        return document;
    }

    public BsonValue serializeValue(Object value) {
        if (EntityControllerManager.hasController(value)) {
            return this.serialize((Entity) value);
        }

        return this.encode(value);
    }

    private BsonValue encode(Object value) {
        for (final Codec codec : Cherry.client().provideCodecs()) {
            if (!codec.isValid(value)) {
                continue;
            }

            return (BsonValue) codec.encode(value);
        }

        return new BsonNull();
    }
}
