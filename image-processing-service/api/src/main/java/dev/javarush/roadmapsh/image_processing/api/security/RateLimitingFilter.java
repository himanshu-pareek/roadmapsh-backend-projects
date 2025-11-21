package dev.javarush.roadmapsh.image_processing.api.security;

import dev.javarush.roadmapsh.image_processing.core.rate_limiting.RateLimitResult;
import dev.javarush.roadmapsh.image_processing.core.rate_limiting.RateLimiter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class RateLimitingFilter implements Filter {
  private final RateLimiter globalLimiter;
  private final RateLimiter defaultUserLimiter;

  public RateLimitingFilter(
      RateLimiter globalLimiter,
      RateLimiter defaultUserLimiter
  ) {
    this.globalLimiter = globalLimiter;
    this.defaultUserLimiter = defaultUserLimiter;
  }

  @Override
  public void doFilter(
      ServletRequest req,
      ServletResponse res,
      FilterChain filterChain
  ) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    RateLimitResult rateLimitResult = checkLimit(request);

    if (!rateLimitResult.allowed()) {
      response.setStatus(429);
      response.setHeader("Retry-After", "1");
      response.getWriter().write("Too Many Requests");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private static String getUserId(HttpServletRequest request) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null) {
      return request.getRemoteAddr();
    }
    return authentication.getName();
  }

  private RateLimitResult checkLimit(HttpServletRequest request) {
    RateLimitResult rateLimitResult = new RateLimitResult(true);
    // 1. Global
    if (globalLimiter != null) {
      rateLimitResult = this.globalLimiter.checkLimit("GLOBAL");
      if (!rateLimitResult.allowed()) {
        return rateLimitResult;
      }
    }

    // 2. Per-user
    if (defaultUserLimiter != null) {
      String userKey = "USER:" + getUserId(request);
      rateLimitResult = this.defaultUserLimiter.checkLimit(userKey);
      if (!rateLimitResult.allowed()) {
        return rateLimitResult;
      }
    }
    return rateLimitResult;
  }
}
