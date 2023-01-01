package net.cherry.codec;

public interface Decoder<T, U> {

    T decode(U toDecode);
}