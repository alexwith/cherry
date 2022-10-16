package net.cherry.query;

import net.cherry.enums.SortingOrder;

public interface QueryOptions {

    QueryOptions endsWith(String value);

    QueryOptions sortBy(SortingOrder order);
}
