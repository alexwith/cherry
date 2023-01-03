package net.cherry.serialization;

import net.cherry.Cherry;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityStorage;
import org.bson.BsonDocument;

public class MongoEntityDeserializer implements EntityDeserializer<BsonDocument> {

    @Override
    public <U extends Entity<U>> U deserialize(Class<U> identifier, BsonDocument serializedEntity) {
        final U entity = Cherry.create(identifier);
        final EntityController<U> controller = entity.getController();
        final EntityStorage storage = controller.getStorage();

        return entity;
    }
}
