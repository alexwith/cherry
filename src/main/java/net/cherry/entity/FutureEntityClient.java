package net.cherry.entity;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.cherry.query.Query;

public interface FutureEntityClient {

    static <T> CompletableFuture<T> findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        EntityClient.validateIdentifier(identifier);

        return null;
    }
}
