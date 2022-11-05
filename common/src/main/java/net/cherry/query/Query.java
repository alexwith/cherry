package net.cherry.query;

import java.util.function.Consumer;
import net.cherry.query.impl.QueryImpl;

public interface Query {

    static Query create() {
        return new QueryImpl();
    }

    Query all();

    Query all(Consumer<QueryOptions> optionsConsumer);

    Query where(String field, Object value);

    Query where(String field, Consumer<QueryOptions> optionsConsumer);
}
