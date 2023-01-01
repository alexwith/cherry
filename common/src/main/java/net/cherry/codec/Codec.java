package net.cherry.codec;

public interface Codec<T, U> extends Encoder<T, U>, Decoder<T, U> {

    boolean isValid(Object object);
}
