package net.cherry.query;

import java.util.Arrays;
import net.cherry.query.impl.QueryImpl;

public interface Query {

    static Query all() {
        return new QueryImpl(QueryOperation.ALL, null);
    }

    static Query equals(String field, Object value) {
        return new QueryImpl(QueryOperation.EQUALS, field, value);
    }

    static Query equalsEither(String field, Object... values) {
        return new QueryImpl(QueryOperation.EQUALS_EITHER, field, values);
    }

    static Query greaterThan(String field, Object value) {
        return new QueryImpl(QueryOperation.GREATER_THAN, field, value);
    }

    static Query lessThan(String field, Object value) {
        return new QueryImpl(QueryOperation.LESS_THAN, field, value);
    }

    static Query notEquals(String field, Object value) {
        return new QueryImpl(QueryOperation.NOT_EQUALS, field, value);
    }

    static Query and(Query... queries) {
        return new QueryImpl(QueryOperation.AND, null, Arrays.stream(queries).toArray());
    }

    static Query or(Query... queries) {
        return new QueryImpl(QueryOperation.OR, null, Arrays.stream(queries).toArray());
    }

    QueryOperation getOperation();

    String getField();

    Object[] getValues();
}
