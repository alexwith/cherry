package net.cherry.codec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.cherry.type.FieldType;

public class CodecRegistry {
    private final List<Codec<?, ?>> codecs = new ArrayList<>();
    private final Map<Class<?>, Codec<?, ?>> codecLookupCache = new HashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Codec lookup(FieldType fieldType) {
        return this.codecLookupCache.computeIfAbsent(fieldType.getType(), (type) -> {
            for (final Codec codec : this.codecs) {
                if (!codec.isValid(type)) {
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
