package com.n11.restaurantservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.n11.restaurantservice.common.base.BaseRestResponse;
import com.n11.restaurantservice.document.Restaurant;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.repository.RestaurantRepository;
import com.n11.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.geo.Distance;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Mehmet Akif Tanisik
 */
@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<BaseRestResponse<RestaurantResponse>> save(@RequestBody @Valid RestaurantSaveRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.save(request)), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRestResponse<RestaurantResponse>> getById(@PathVariable String id) {
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.getById(id)), OK);
    }

    @GetMapping
    public ResponseEntity<BaseRestResponse<List<RestaurantResponse>>> getAll() {
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.getAll()), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseRestResponse<RestaurantResponse>> update(@PathVariable String id, @RequestBody @Valid RestaurantUpdateRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.update(id, request)), OK);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<BaseRestResponse<RestaurantResponse>> deactivate(@PathVariable String id) {
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.deactivate(id)), OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String id) {
        restaurantService.delete(id);
    }

}
