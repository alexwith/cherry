package net.cherry.client;

import java.util.function.Consumer;
import net.cherry.query.Query;
import net.cherry.query.impl.QueryImpl;

public interface CherryClient {

    void connect();

    <T> T create(T proxiedEntity);

    <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer);

    <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer);

    <T> int count(Class<T> identifier, Consumer<Query> queryConsumer);

    default Query handleQueryConsumer(Consumer<Query> queryConsumer) {
        final Query query = new QueryImpl();
        queryConsumer.accept(query);

        return query;
    }
}
