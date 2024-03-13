package com.n1.recommendationservice.controller;

import com.n1.recommendationservice.common.base.BaseRestResponse;
import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mehmet Akif Tanisik
 */
@RestController
@RequestMapping("/api/v1/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationServiceImpl;


    @GetMapping("/{userId}")
    public ResponseEntity<BaseRestResponse<List<RestaurantResponse>>> getRecommendedRestaurants(@PathVariable Long userId) {
        return new ResponseEntity<>(BaseRestResponse.of(recommendationServiceImpl.getRecommendedRestaurants(userId)), OK);
    }
}
