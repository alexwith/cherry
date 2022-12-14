package net.cherry;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.cherry.client.CherryClient;
import net.cherry.codec.CodecRegistry;
import net.cherry.entity.Entity;
import net.cherry.proxy.ProxyFactory;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.query.Query;
import net.cherry.thread.CherryExecutor;

public class Cherry {
    private static CherryClient client;

    private static final CodecRegistry CODEC_REGISTRY = new CodecRegistry();

    public static CherryClient connect(CherryClient client) {
        Cherry.client = client;

        client.connect();
        CODEC_REGISTRY.register(client.provideCodecs());

        return client;
    }

    public static CherryClient client() {
        return client;
    }

    public static CodecRegistry codecRegistry() {
        return CODEC_REGISTRY;
    }

    public static <T extends Entity<T>> T create(Class<T> identifier) {
        final ProxiedClass<T> proxiedClass = ProxyFactory.createProxiedClass(identifier);
        return ProxyFactory.createProxiedEntity(proxiedClass, null);
    }

    public static <T extends Entity<T>> T createWithId(Class<T> identifier, Object id) {
        final ProxiedClass<T> proxiedClass = ProxyFactory.createProxiedClass(identifier);
        return ProxyFactory.createProxiedEntity(proxiedClass, id);
    }

    public static <T extends Entity<T>> Collection<T> findMany(Class<T> identifier, Query query) {
        Cherry.validateIdentifier(identifier);

        return client().findMany(identifier, query);
    }

    public static <T extends Entity<T>> T findOne(Class<T> identifier, Query query) {
        Cherry.validateIdentifier(identifier);

        return client().findOne(identifier, query);
    }

    public static <T extends Entity<T>> long count(Class<T> identifier, Query query) {
        Cherry.validateIdentifier(identifier);

        return client().count(identifier, query);
    }

    public static <T extends Entity<T>> CompletableFuture<Collection<T>> findManyFuture(Class<T> identifier, Query query) {
        return CherryExecutor.supplyFuture(() -> Cherry.findMany(identifier, query));
    }

    public static <T extends Entity<T>> CompletableFuture<T> findOneFuture(Class<T> identifier, Query query) {
        return CherryExecutor.supplyFuture(() -> Cherry.findOne(identifier, query));
    }

    public static <T extends Entity<T>> CompletableFuture<Long> countFuture(Class<T> identifier, Query query) {
        return CherryExecutor.supplyFuture(() -> Cherry.count(identifier, query));
    }

    private static void validateIdentifier(Class<?> identifier) {
        if (!Entity.class.isAssignableFrom(identifier)) {
            throw new IllegalStateException("EntityClient was provided with a non-entity object.");
        }
    }
}
