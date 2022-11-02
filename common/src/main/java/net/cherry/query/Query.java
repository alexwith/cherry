package net.cherry.query;

import java.util.function.Consumer;

public interface Query {

    Query all();

    Query all(Consumer<QueryOptions> optionsConsumer);

    Query where(String field, Object value);

    Query where(String field, Consumer<QueryOptions> optionsConsumer);
}
