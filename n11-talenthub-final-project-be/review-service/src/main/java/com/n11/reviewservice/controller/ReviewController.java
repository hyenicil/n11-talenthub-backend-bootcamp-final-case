package com.n11.reviewservice.controller;

import com.n11.reviewservice.common.base.BaseRestResponse;
import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;
import com.n11.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Mehmet Akif Tanisik
 */
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<BaseRestResponse<ReviewResponse>> save(@RequestBody @Valid ReviewSaveRequest request) throws IOException {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.save(request)), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRestResponse<ReviewResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.getById(id)), OK);
    }

    @GetMapping
    public ResponseEntity<BaseRestResponse<List<ReviewResponse>>> getAll() {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.getAll()), OK);
    }

    @GetMapping("/with-userId/{userId}")
    public ResponseEntity<BaseRestResponse<List<ReviewResponse>>> getAllByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.getAllByUserId(userId)), OK);
    }

    @GetMapping("/with-restaurantId/{restaurantId}")
    public ResponseEntity<BaseRestResponse<List<ReviewResponse>>> getAllByRestaurantId(@PathVariable String restaurantId) {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.getAllByRestaurantId(restaurantId)), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseRestResponse<ReviewResponse>> update(@PathVariable Long id, @RequestBody @Valid ReviewUpdateRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(reviewService.update(id, request)), OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }

}
