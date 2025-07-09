package dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy;

import java.net.http.HttpResponse;

public interface HttpProxy {
    HttpResponse<byte[]> get(String url);

    default void clear() {}
}
