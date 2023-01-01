package net.cherry.entity;

import java.util.function.UnaryOperator;

public interface Entity<T> {

    UnaryOperator<EntitySettings<T>> settings();
}
