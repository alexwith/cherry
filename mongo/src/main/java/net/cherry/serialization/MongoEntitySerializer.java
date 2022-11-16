package net.cherry.serialization;

import java.util.Map.Entry;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.entity.EntityStorage;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

public class MongoEntitySerializer implements EntitySerializer<BsonDocument> {

    @Override
    public <T> BsonDocument serialize(T entity) {
        final EntityController<T> controller = EntityControllerManager.getController(entity);
        final EntityStorage storage = controller.getStorage();

        final BsonDocument document = new BsonDocument();
        for (final Entry<String, Object> field : storage.getValues().entrySet()) {
            document.put(field.getKey(), this.serializeValue(field.getValue()));
        }

        return document;
    }

    private BsonValue serializeValue(Object value) {
        if (EntityControllerManager.hasController(value)) {
            return this.serialize(value);
        }

        if (value instanceof String) {
            return new BsonString((String) value);
        }

        return new BsonString("field");
    }
}
