package com.example.localcuisine.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Save to cache with expiration
    public void put(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    // Get from cache
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Invalidate cache
    public void evict(String key) {
        redisTemplate.delete(key);
    }
}

