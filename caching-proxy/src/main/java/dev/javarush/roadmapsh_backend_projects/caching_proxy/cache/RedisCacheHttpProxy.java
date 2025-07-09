package dev.javarush.roadmapsh_backend_projects.caching_proxy.cache;

import dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.http.HttpResponse;
import java.time.Duration;

public class RedisCacheHttpProxy implements HttpProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheHttpProxy.class);

    private final HttpProxy delegate;
    private final RedisTemplate<String, HttpResponse<byte[]>> cache;

    @Value("${cache.duration.seconds}")
    private int cacheDurationSeconds;

    public RedisCacheHttpProxy(HttpProxy delegate, RedisConnectionFactory factory) {
        this.delegate = delegate;
        this.cache = new RedisTemplate<>();
        this.cache.setConnectionFactory(factory);
        this.cache.afterPropertiesSet();
    }

    @Override
    public HttpResponse<byte[]> get(String url) {
        LOGGER.info("Getting response for {}", url);
        HttpResponse<byte[]> response = getFromCache(url);
        boolean cached = response != null;
        if (response != null) {
            LOGGER.info("Cache hit for {}", url);
        } else {
            LOGGER.info("Cache miss for {}", url);
            response = delegate.get(url);
            putIntoCache(url, response);
        }
        return new CachedHttpResponse<>(response, cached);
    }

    private HttpResponse<byte[]> getFromCache(String url) {
        return cache.opsForValue().get(url);
    }

    private void putIntoCache(String url, HttpResponse<byte[]> response) {
        cache.opsForValue().set(url, new MaterialHttpResponse<>(response), Duration.ofSeconds(cacheDurationSeconds));
    }

    @Override
    public void clear() {
        HttpProxy.super.clear();
    }
}
