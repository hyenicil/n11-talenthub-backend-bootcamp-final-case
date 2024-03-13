package com.n11.reviewservice.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mehmet Akif Tanisik
 */
@FeignClient(value = "review-user-client", url = "http://localhost:8090/api/v1/users")
@Component
public interface UserClient {

    @GetMapping("/{userId}")
    Response getUserById(@PathVariable Long userId);
}

