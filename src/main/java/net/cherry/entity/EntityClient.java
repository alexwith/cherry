package net.cherry.entity;

import java.util.function.Consumer;
import net.cherry.annotation.Entity;
import net.cherry.query.Query;

public interface EntityClient {

    static <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        EntityClient.validateIdentifier(identifier);

        return null;
    }

    static <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        EntityClient.validateIdentifier(identifier);

        return null;
    }

    static <T> T count(Class<T> identifier, Consumer<Query> queryConsumer) {
        EntityClient.validateIdentifier(identifier);

        return null;
    }

    static void validateIdentifier(Class<?> identifier) {
        if (identifier.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("EntityClient was provided with a non-entity object.");
        }
    }
}
