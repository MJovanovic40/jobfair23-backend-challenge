package com.nordeus.jobfair.auctionservice.auctionservice.security.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BucketConfig {
    @Value("${security.rate-limit.requests-per-minute}")
    private int requestsPerMinute;

    @Bean
    public Map<String, Bucket> buckets() {
        return new ConcurrentHashMap<>();
    }

    public Bucket resolveBucket(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return buckets().computeIfAbsent(ip, this::createNewBucket);
    }

    private Bucket createNewBucket(String ip) {
        Refill refill = Refill.greedy(this.requestsPerMinute, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(this.requestsPerMinute, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
