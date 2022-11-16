package net.cherry.serialization;

public interface EntitySerializer<T> {

    <U> T serialize(U entity);
}
