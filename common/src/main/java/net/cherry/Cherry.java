package net.cherry;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.cherry.annotation.Entity;
import net.cherry.client.CherryClient;
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

    public static <T> T create(Class<T> identifier) {
        final ProxiedClass<T> proxiedClass = ProxyFactory.createProxiedClass(identifier);
        final T proxiedEntity = ProxyFactory.createProxiedEntity(proxiedClass);

        return Cherry.client.create(proxiedEntity);
    }

    public static <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return Cherry.client.findMany(identifier, queryConsumer);
    }

    public static <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return Cherry.client.findOne(identifier, queryConsumer);
    }

    public static <T> int count(Class<T> identifier, Consumer<Query> queryConsumer) {
        Cherry.validateIdentifier(identifier);

        return Cherry.client.count(identifier, queryConsumer);
    }

    public static <T> CompletableFuture<T> findManyFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.findMany(identifier, queryConsumer));
    }

    public static <T> CompletableFuture<T> findOneFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.findOne(identifier, queryConsumer));
    }

    public static <T> CompletableFuture<Integer> countFuture(Class<T> identifier, Consumer<Query> queryConsumer) {
        return CherryExecutor.supplyFuture(() -> Cherry.count(identifier, queryConsumer));
    }

    private static void validateIdentifier(Class<?> identifier) {
        if (!identifier.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("EntityClient was provided with a non-entity object.");
        }
    }
}
