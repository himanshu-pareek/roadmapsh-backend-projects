package dev.javarush.roadmapsh_backend_projects.caching_proxy.cache;

import dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryHttpProxy implements HttpProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryHttpProxy.class);

    Map<String, HttpResponse<byte[]>> cache = new ConcurrentHashMap<>();

    private final HttpProxy delegate;

    public InMemoryHttpProxy(HttpProxy delegate) {
        this.delegate = delegate;
    }

    @Override
    public HttpResponse<byte[]> get(String url) {
        LOGGER.info("Getting response for {}", url);
        HttpResponse<byte[]> response;
        boolean cached = false;
        if (cache.containsKey(url)) {
            LOGGER.info("Cache hit for {}", url);
            response = cache.get(url);
            cached = true;
        } else {
            LOGGER.info("Cache miss for {}", url);
            response = delegate.get(url);
            cache.put(url, response);
        }
        return new CachedHttpResponse<>(response, cached);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }
}
