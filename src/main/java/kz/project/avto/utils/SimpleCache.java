package kz.project.avto.utils;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache {

    private static SimpleCache instance;

    private final Map<String, Object> cache = new HashMap<>();

    private SimpleCache() {
    }

    public static SimpleCache getInstance() {
        if (instance == null) {
            instance = new SimpleCache();
        }
        return instance;
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
