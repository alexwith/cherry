package net.cherry;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.cherry.client.CherryClient;
import net.cherry.entity.Entity;
import net.cherry.proxy.ProxyFactory;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.query.Query;
import net.cherry.thread.CherryExecutor;

public class Cherry {
    private static CherryClient client;

    public static CherryClient connect(CherryClient client) {
        Cherry.client = client;

        client.connect();

        return client;
    }

    public static CherryClient client() {
        return client;
    }

    public static <T extends Entity<T>> T create(Class<T> identifier) {
        final ProxiedClass<T> proxiedClass = ProxyFactory.createProxiedClass(identifier);
        final T proxiedEntity = ProxyFactory.createProxiedEntity(proxiedClass);

        return client().save(proxiedEntity);
    }

    public static <T> Collection<T> findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return client().findMany(identifier, queryConsumer);
    }

    public static <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return client().findOne(identifier, queryConsumer);
    }

    public static <T> int count(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return client().count(identifier, queryConsumer);
    }

    public static <T> CompletableFuture<Collection<T>> findManyFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.findMany(identifier, queryConsumer));
    }

    public static <T> CompletableFuture<T> findOneFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.findOne(identifier, queryConsumer));
    }

    public static <T> CompletableFuture<Integer> countFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.count(identifier, queryConsumer));
    }

    private static void validateIdentifier(Class<?> identifier) {
        if (!Entity.class.isAssignableFrom(identifier)) {
            throw new IllegalStateException("EntityClient was provided with a non-entity object.");
        }
    }
}
