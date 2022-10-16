package net.cherry.util;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// very simple BiMap implementation only meant for Cherry, as its thread safe only to some degree
public class BiMap<K, V> extends IdentityHashMap<K, V> {
    private final Map<V, K> invertedMap = new IdentityHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Map<V, K> inverted() {
        return this.invertedMap;
    }

    @Override
    public V get(Object key) {
        final Lock lock = this.lock.readLock();
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        final Lock lock = this.lock.writeLock();
        lock.lock();
        try {
            this.invertedMap.put(value, key);
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        final Lock lock = this.lock.writeLock();
        lock.lock();
        try {
            this.invertedMap.remove(value, key);
            return super.remove(key, value);
        } finally {
            lock.unlock();
        }
    }
}
