package org.example.cafe.cache;

import org.springframework.stereotype.Component;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MethodsCaches<K,V> {
    private final Map<K, SoftReference<V>> cache = new ConcurrentHashMap<>();

    public Optional<V> get(K key) {
        SoftReference<V> ref = cache.get(key);
        return (ref != null) ? Optional.ofNullable(ref.get()) : Optional.empty();
    }

    public void put(K key,V value) {
        cache.put(key,new SoftReference<>(value));
    }
}
