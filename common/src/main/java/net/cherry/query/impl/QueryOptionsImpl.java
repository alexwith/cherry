package net.cherry.query.impl;

import java.util.Optional;
import net.cherry.enums.SortingOrder;
import net.cherry.query.QueryOptions;

public class QueryOptionsImpl implements QueryOptions {
    private String endsWithOption;
    private SortingOrder sortingOrderOption;

    public Optional<String> getEndsWithOption() {
        return Optional.ofNullable(this.endsWithOption);
    }

    public Optional<SortingOrder> getSortingOrderOption() {
        return Optional.ofNullable(this.sortingOrderOption);
    }

    @Override
    public QueryOptions endsWith(String value) {
        this.endsWithOption = value;
        return this;
    }

    @Override
    public QueryOptions sortBy(SortingOrder order) {
        return this;
    }
}
