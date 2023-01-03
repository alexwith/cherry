package net.cherry.entity;

import java.util.function.UnaryOperator;

public interface Entity<T extends Entity<T>> {

    /**
     * This method is overridden by byte buddy upon creation of the proxied entity
     *
     * @return The overridden controller
     */
    default EntityController<T> getController() {
        return null;
    }

    UnaryOperator<EntitySettings<T>> settings();
}
