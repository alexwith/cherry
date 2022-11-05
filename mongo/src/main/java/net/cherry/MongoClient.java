package net.cherry;

import java.util.function.Consumer;
import net.cherry.client.CherryClient;
import net.cherry.query.Query;

public class MongoClient implements CherryClient {

    @Override
    public void open() {

    }

    @Override
    public <T> T findMany(Class<T> identifier, Consumer<Query> queryConsumer) {
        return null;
    }

    @Override
    public <T> T findOne(Class<T> identifier, Consumer<Query> queryConsumer) {
        return null;
    }

    @Override
    public <T> int count(Class<T> identifier, Consumer<Query> queryConsumer) {
        return 0;
    }
}
