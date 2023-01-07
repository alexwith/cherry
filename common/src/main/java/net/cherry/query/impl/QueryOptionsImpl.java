package net.cherry.query.impl;

import net.cherry.enums.SortingOrder;
import net.cherry.query.QueryOptions;

public class QueryOptionsImpl implements QueryOptions {

    @Override
    public QueryOptions endsWith(String value) {
        return this;
    }

    @Override
    public QueryOptions sortBy(SortingOrder order) {
        return this;
    }
}
