package net.cherry.codec;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodecRegistry {
    private final List<Codec<?, ?>> codecs = new ArrayList<>();
    private final Map<Type, Codec<?, ?>> codecLookupCache = new HashMap<>();

    @SuppressWarnings("rawtypes")
    public Codec lookup(Type type) {
        return this.codecLookupCache.computeIfAbsent(type, ($) -> {
            for (final Codec codec : this.codecs) {
                if (!codec.isValid((Class<?>) type)) {
                    continue;
                }

                return codec;
            }

            return null;
        });
    }

    public void register(Codec<?, ?>... codecs) {
        this.register(List.of(codecs));
    }

    public void register(Collection<Codec<?, ?>> codecs) {
        this.codecs.addAll(codecs);
    }
}
