package net.cherry.client;

import java.util.function.Consumer;
import net.cherry.query.Query;

public interface CherryClient {

    void open();

    <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer);

    <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer);

    <T> int count(Class<T> identifier, Consumer<Query> queryConsumer);
}
