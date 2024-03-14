package com.n11.reviewservice.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mehmet Akif Tanisik
 */
@FeignClient("user-service/api/v1/users")
public interface UserClient {

    @GetMapping("/{userId}")
    Response getUserById(@PathVariable Long userId);
}

