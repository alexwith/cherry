package net.cherry.codec;

import java.lang.reflect.Type;

public interface Decoder<T, U> {

    T decode(U toDecode, Type[] typeArgs);
}