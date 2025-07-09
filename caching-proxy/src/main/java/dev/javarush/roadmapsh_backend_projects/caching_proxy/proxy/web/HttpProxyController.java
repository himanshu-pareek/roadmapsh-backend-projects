package dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.web;

import dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.HttpProxy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
public class HttpProxyController {

    private final HttpProxy httpProxy;

    @Value("${proxy.upstream.server.host}")
    private String upstreamServerHost;

    public HttpProxyController(HttpProxy httpProxy) {
        this.httpProxy = httpProxy;
    }

    @GetMapping("**")
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        HttpResponse<byte[]> res = this.httpProxy.get(upstreamServerHost + requestURI);
        copy(res, response);
    }

    @PostMapping("cache/clear")
    void clearCache() {
        this.httpProxy.clear();
    }

    private void copy(HttpResponse<byte[]> res, HttpServletResponse response) throws IOException {
        response.setStatus(res.statusCode());
        res.headers().map().forEach((key, values) -> {
            values.forEach(value -> {
                response.addHeader(key, value);
            });
        });
        response.getOutputStream().write(res.body());
    }

}
