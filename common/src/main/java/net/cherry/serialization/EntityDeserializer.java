package net.cherry.serialization;

import net.cherry.entity.Entity;

public interface EntityDeserializer<T> {

    <U extends Entity<U>> U deserialize(Class<U> identifier, T serializedEntity);
}
