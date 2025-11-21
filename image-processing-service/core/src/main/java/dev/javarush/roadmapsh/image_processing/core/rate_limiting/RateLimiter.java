package dev.javarush.roadmapsh.image_processing.core.rate_limiting;

public interface RateLimiter {
  RateLimitResult checkLimit(String key);
}
