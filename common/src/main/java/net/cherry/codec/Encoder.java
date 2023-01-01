package net.cherry.codec;

public interface Encoder<T, U> {

    U encode(T toEncode);
}