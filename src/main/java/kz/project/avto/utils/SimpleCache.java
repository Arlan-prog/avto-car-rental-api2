package kz.project.avto.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class SimpleCache {
    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

    private SimpleCache() {
    }

    private static final class Holder {
        private static final SimpleCache INSTANCE = new SimpleCache();
    }

    public static SimpleCache getInstance() {
        return Holder.INSTANCE;
    }

    public void put(String key, Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cache key and value must not be null.");
        }
        cache.put(key, value);
    }

    public Object get(String key) {
        return key == null ? null : cache.get(key);
    }

    public void remove(String key) {
        if (key != null) {
            cache.remove(key);
        }
    }

    public void clear() {
        cache.clear();
    }
}
