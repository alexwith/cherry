package net.cherry.codec;

import org.bson.BsonValue;

public interface MongoCodec<T> extends Codec<T, BsonValue> {}
