package dev.javarush.roadmapsh.image_processing.redis.config.rate_limit;

import dev.javarush.roadmapsh.image_processing.core.rate_limiting.RateLimitResult;
import dev.javarush.roadmapsh.image_processing.core.rate_limiting.RateLimiter;
import dev.javarush.roadmapsh.image_processing.redis.config.RedisConfiguration;
import java.time.Instant;
import java.util.Arrays;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

public class RedisTokenBucketRateLimiter implements RateLimiter {
  private final StringRedisTemplate redisTemplate;
  private final DefaultRedisScript<Long> script;


  private final int capacity;
  private final double refillPerMs; // tokens per millisecond
  private final long keyExpireMillis;


  // Lua script string copied from above (without comments)
  private static final String LUA_SCRIPT =
      "local tokens_key = KEYS[1]\n" +
          "local timestamp_key = KEYS[2]\n" +
          "local now = tonumber(ARGV[1])\n" +
          "local refill_rate = tonumber(ARGV[2])\n" +
          "local capacity = tonumber(ARGV[3])\n" +
          "local expire_ms = tonumber(ARGV[4])\n" +
          "local tokens = tonumber(redis.call('get', tokens_key) or capacity)\n" +
          "local last_ts = tonumber(redis.call('get', timestamp_key) or 0)\n" +
          "local delta = math.max(0, now - last_ts)\n" +
          "local added = delta * refill_rate\n" +
          "tokens = math.min(capacity, tokens + added)\n" +
          "local allowed = 0\n" +
          "if tokens >= 1 then\n" +
          " tokens = tokens - 1\n" +
          " allowed = 1\n" +
          "else\n" +
          " allowed = 0\n" +
          "end\n" +
          "redis.call('set', tokens_key, tokens)\n" +
          "redis.call('set', timestamp_key, now)\n" +
          "redis.call('pexpire', tokens_key, expire_ms)\n" +
          "redis.call('pexpire', timestamp_key, expire_ms)\n" +
          "return allowed";

  public RedisTokenBucketRateLimiter(
      RedisConfiguration redisConfiguration,
      int capacity,
      double refillPerSecond,
      long keyExpireMillis
  ) {
    this.redisTemplate = new StringRedisTemplate();
    this.redisTemplate.setConnectionFactory(redisConfiguration.getConnectionFactory());
    this.redisTemplate.afterPropertiesSet();
    this.capacity = capacity;
    this.refillPerMs = refillPerSecond / 1000.0d;
    this.keyExpireMillis = keyExpireMillis;


    this.script = new DefaultRedisScript<>();
    this.script.setScriptText(LUA_SCRIPT);
    this.script.setResultType(Long.class);
  }


  @Override
  public RateLimitResult checkLimit(String key) {
    final String tokensKey = "rate:tokens:" + key;
    final String tsKey = "rate:ts:" + key;
    String now = String.valueOf(Instant.now().toEpochMilli());
    String refillStr = String.valueOf(this.refillPerMs);
    String capacityStr = String.valueOf(this.capacity);
    String expireStr = String.valueOf(this.keyExpireMillis);


    Long result = redisTemplate.execute(
            script,
            Arrays.asList(tokensKey, tsKey),
            now, refillStr, capacityStr, expireStr
        );
    return new RateLimitResult(result == 1L);
  }
}