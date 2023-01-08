package net.cherry.client;

import java.util.Collection;
import java.util.function.Consumer;
import net.cherry.codec.Codec;
import net.cherry.entity.Entity;
import net.cherry.query.Query;
import net.cherry.query.impl.QueryImpl;

public interface CherryClient {

    void connect();

    <T extends Entity<T>> T save(T proxiedEntity);

    <T extends Entity<T>> Collection<T> findMany(Class<T> identifier, Query query);

    <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer);

    <T> int count(Class<T> identifier, Consumer<Query> queryConsumer);

    Collection<Codec<?, ?>> provideCodecs();

    default Query handleQueryConsumer(Consumer<Query> queryConsumer) {
        final Query query = null;//new QueryImpl();
        queryConsumer.accept(query);

        return query;
    }
}
