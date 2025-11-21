package dev.javarush.roadmapsh.image_processing.redis.config;

import java.time.Duration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class RedisConfiguration {
    private final RedisConnectionFactory connectionFactory;

  public RedisConfiguration(String redisHost, int redisPort) {
    this.connectionFactory = createConnectionFactory(redisHost, redisPort);
  }

    private RedisConnectionFactory createConnectionFactory(
        String redisHost,
        int redisPort
    ) {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();
        var connectionFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort), clientConfig);
        connectionFactory.start();
        return connectionFactory;
    }

  public RedisConnectionFactory getConnectionFactory() {
    return connectionFactory;
  }
}
