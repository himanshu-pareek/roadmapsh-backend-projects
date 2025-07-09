package dev.javarush.roadmapsh_backend_projects.caching_proxy.cache;

import javax.net.ssl.SSLSession;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MaterialHttpResponse<T> implements HttpResponse<T>, Serializable {

    private final int statusCode;
    private final URI uri;
    private final Map<String, List<String>> headersMap;
    private final T body;
    private final HttpClient.Version version;

    public MaterialHttpResponse(HttpResponse<T> response) {
        this.statusCode = response.statusCode();
        this.uri = response.uri();
        this.headersMap = response.headers().map();
        this.body = response.body();
        this.version = response.version();
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public HttpRequest request() {
        return HttpRequest.newBuilder(uri).build();
    }

    @Override
    public Optional<HttpResponse<T>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpHeaders headers() {
        return HttpHeaders.of(headersMap, (k, v) -> true);
    }

    @Override
    public T body() {
        return body;
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public HttpClient.Version version() {
        return version;
    }
}
