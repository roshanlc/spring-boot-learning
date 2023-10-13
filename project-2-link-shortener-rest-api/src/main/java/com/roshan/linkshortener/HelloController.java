package com.roshan.linkshortener;


import com.roshan.linkshortener.models.Link;
import com.roshan.linkshortener.utilities.ShortName;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/api")
public class HelloController {
    private final Map<String, Bucket> rateLimiters = new ConcurrentHashMap<>();

    // limit of 100 request per minute
    private final Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofSeconds(60)));


    @Autowired
    private RedisTemplate<String, String> template;

    @Operation(summary = "Get the original link represented by short link.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the original link",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Link.class))})})
    @GetMapping("/{shortLink}")
    public Link getActualLink(@PathVariable(name = "shortLink", required = true) String shortLink, HttpServletResponse response, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        Bucket rateLimiter = rateLimiters.computeIfAbsent(clientIp, (k) -> {
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        });

        ConsumptionProbe probe = rateLimiter.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            String actualLink = template.opsForValue().get(shortLink);
            if (actualLink == null) {
                response.setStatus(404);
            }
            return new Link(shortLink, actualLink);
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        return new Link(shortLink, null);
    }

    @PostMapping(value = "/")
    public Link addNewLink(@RequestParam(value = "link", required = true) String link, HttpServletResponse response, HttpServletRequest request) {
        try {
            String clientIp = request.getRemoteAddr();
            Bucket rateLimiter = rateLimiters.computeIfAbsent(clientIp, (k) -> {
                return Bucket.builder()
                        .addLimit(limit)
                        .build();
            });

            ConsumptionProbe probe = rateLimiter.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
            String shortLink = ShortName.generateShortLink(link);
            template.opsForValue().set(shortLink, link);
            return new Link(shortLink, link);
            }

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return new Link(null,link);
        } catch (Exception e) {
            response.setStatus(500);
            return new Link(null, link);
        }
    }

}

