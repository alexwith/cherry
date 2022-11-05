package net.cherry.query.impl;

import java.util.function.Consumer;
import net.cherry.query.Query;
import net.cherry.query.QueryOptions;

public class QueryImpl implements Query {

    @Override
    public Query all() {
        return null;
    }

    @Override
    public Query all(Consumer<QueryOptions> optionsConsumer) {
        return null;
    }

    @Override
    public Query where(String field, Object value) {
        return null;
    }

    @Override
    public Query where(String field, Consumer<QueryOptions> optionsConsumer) {
        return null;
    }
}
