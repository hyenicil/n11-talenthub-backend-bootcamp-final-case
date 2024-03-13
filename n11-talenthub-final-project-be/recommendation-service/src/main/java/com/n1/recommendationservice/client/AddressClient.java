package com.n1.recommendationservice.client;

import com.n1.recommendationservice.common.base.BaseRestResponse;
import com.n1.recommendationservice.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@FeignClient(value = "address", url = "http://localhost:8090/api/v1/addresses")
public interface AddressClient {

    @GetMapping("/with-userId/{userId}")
    ResponseEntity<BaseRestResponse<List<AddressResponse>>> getByUserId(@PathVariable Long userId);
}

