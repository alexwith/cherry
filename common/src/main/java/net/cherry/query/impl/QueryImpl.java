package net.cherry.query.impl;

import net.cherry.query.Query;
import net.cherry.query.QueryOperation;

public class QueryImpl implements Query {
    private final QueryOperation operation;
    private final String field;
    private final Object[] values;

    public QueryImpl(QueryOperation operation, String field, Object... values) {
        this.operation = operation;
        this.field = field;
        this.values = values;
    }

    @Override
    public QueryOperation getOperation() {
        return this.operation;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public Object[] getValues() {
        return this.values;
    }
}
