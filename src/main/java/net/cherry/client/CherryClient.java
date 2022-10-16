package net.cherry.client;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.cherry.annotation.Entity;
import net.cherry.query.Query;
import net.cherry.thread.CherryExecutor;

public interface CherryClient {

    static <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        CherryClient.validateIdentifier(identifier);

        return null;
    }

    static <T> CompletableFuture<T> findManyFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> CherryClient.findMany(identifier, queryConsumer));
    }

    static <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        CherryClient.validateIdentifier(identifier);

        return null;
    }

    static <T> CompletableFuture<T> findOneFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> CherryClient.findOne(identifier, queryConsumer));
    }

    static <T> int count(Class<T> identifier, Consumer<Query> queryConsumer) {
        CherryClient.validateIdentifier(identifier);

        return -1;
    }

    static <T> CompletableFuture<Integer> countFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> CherryClient.count(identifier, queryConsumer));
    }

    static void validateIdentifier(Class<?> identifier) {
        if (identifier.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("EntityClient was provided with a non-entity object.");
        }
    }
}
