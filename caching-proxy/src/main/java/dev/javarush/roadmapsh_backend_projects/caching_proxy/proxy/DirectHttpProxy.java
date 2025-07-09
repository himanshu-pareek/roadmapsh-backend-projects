package dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DirectHttpProxy implements HttpProxy{

    private final HttpClient httpClient;

    public DirectHttpProxy() {
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public HttpResponse<byte[]> get(String url) {
        try {
            return httpClient.send(
                    HttpRequest.newBuilder(URI.create(url)).build(),
                    HttpResponse.BodyHandlers.ofByteArray()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
