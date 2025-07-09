package dev.javarush.roadmapsh_backend_projects.caching_proxy.cache;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachedHttpResponse<T> implements HttpResponse<T> {

    private static final String CACHE_HEADER_KEY = "X-Cache";

    private final HttpResponse<T> delegate;
    private final boolean cached;

    public CachedHttpResponse(HttpResponse<T> delegate, boolean cached) {
        this.delegate = delegate;
        this.cached = cached;
    }

    @Override
    public int statusCode() {
        return delegate.statusCode();
    }

    @Override
    public HttpRequest request() {
        return delegate.request();
    }

    @Override
    public Optional<HttpResponse<T>> previousResponse() {
        return delegate.previousResponse();
    }

    @Override
    public HttpHeaders headers() {
        Map<String, List<String>> originalHeaders = delegate.headers().map();
        Map<String, List<String>> cacheHeaders = Map.of(CACHE_HEADER_KEY, List.of(getCacheHeaderValue()));
        Map<String, List<String>> combinedHeaders = new HashMap<>();
        combinedHeaders.putAll(originalHeaders);
        combinedHeaders.putAll(cacheHeaders);
        return HttpHeaders.of(combinedHeaders, (k, v) -> true);
    }

    private String getCacheHeaderValue() {
        return cached ? "HIT" : "MISS";
    }

    @Override
    public T body() {
        return delegate.body();
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return delegate.sslSession();
    }

    @Override
    public URI uri() {
        return delegate.uri();
    }

    @Override
    public HttpClient.Version version() {
        return delegate.version();
    }
}
