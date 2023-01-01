package net.cherry.serialization;

import net.cherry.entity.Entity;

public interface EntitySerializer<T> {

    <U extends Entity> T serialize(U entity);
}
