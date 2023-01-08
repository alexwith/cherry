package net.cherry.client;

import java.util.Collection;
import net.cherry.Cherry;
import net.cherry.codec.Codec;
import net.cherry.entity.Entity;
import net.cherry.query.Query;

public interface CherryClient {

    void connect();

    <T extends Entity<T>> T save(T proxiedEntity);

    <T extends Entity<T>> Collection<T> findMany(Class<T> identifier, Query query);

    <T extends Entity<T>> T findOne(Class<T> identifier, Query query);

    <T extends Entity<T>> long count(Class<T> identifier, Query query);

    Collection<Codec<?, ?>> provideCodecs();

    default <T extends Entity<T>> String getDatabase(Class<T> identifier) {
        return Cherry.create(identifier).getController().getSettings().getDatabase();
    }
}
