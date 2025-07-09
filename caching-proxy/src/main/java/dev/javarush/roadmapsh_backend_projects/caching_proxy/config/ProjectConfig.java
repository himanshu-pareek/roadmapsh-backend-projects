package dev.javarush.roadmapsh_backend_projects.caching_proxy.config;

import dev.javarush.roadmapsh_backend_projects.caching_proxy.cache.InMemoryHttpProxy;
import dev.javarush.roadmapsh_backend_projects.caching_proxy.cache.RedisCacheHttpProxy;
import dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.DirectHttpProxy;
import dev.javarush.roadmapsh_backend_projects.caching_proxy.proxy.HttpProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class ProjectConfig {
    @Bean
    HttpProxy httpProxy() {
        return new DirectHttpProxy();
    }

//    @Bean
//    @Primary
//    HttpProxy cacheHttpProxy(HttpProxy proxy) {
//        return new InMemoryHttpProxy(proxy);
//    }

    @Bean
    @Primary
    HttpProxy cacheHttpProxy(HttpProxy proxy, RedisConnectionFactory factory) {
        return new RedisCacheHttpProxy(proxy, factory);
    }
}
