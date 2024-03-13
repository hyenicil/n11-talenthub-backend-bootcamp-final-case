package com.n11.apigateway.filter;

import com.n11.apigateway.model.LogObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Mehmet Akif Tanisik
 */
@Component
public class GlobalLoggingFilter extends OncePerRequestFilter implements Ordered {
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.application.name}")
    private String applicationName;

    public GlobalLoggingFilter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Instant startTime = Instant.now();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        byte[] wrappedRequestBody = wrappedRequest.getContentAsByteArray();
        byte[] wrappedResponseBody = wrappedResponse.getContentAsByteArray();

        String requestBodyStr = new String(wrappedRequestBody, StandardCharsets.UTF_8);
        String responseBodyStr = new String(wrappedResponseBody, StandardCharsets.UTF_8);

        Instant endTime = Instant.now();
        Duration latency = Duration.between(startTime, endTime);

        wrappedResponse.copyBodyToResponse();

        LogObject logObject = LogObject.builder()
                .logTime(LocalDateTime.now())
                .method(request.getMethod())
                .requestUri(request.getRequestURI())
                .requestBodyString(requestBodyStr)
                .responseStatusCode(response.getStatus())
                .responseBodyString(responseBodyStr)
                .contentType(request.getContentType())
                .sourceApi(applicationName)
                .responseLatency(latency.toMillisPart())
                .build();


        rabbitTemplate.convertAndSend("gateway.logging.queue.exchange", "gateway.logging.queue.routing-key", logObject);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri.contains("/swagger-ui") || requestUri.contains("/v3/api-docs");
    }
}
