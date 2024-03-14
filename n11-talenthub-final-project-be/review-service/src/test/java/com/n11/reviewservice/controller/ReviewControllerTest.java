package com.n11.reviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;
import com.n11.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService mockReviewService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void shouldSaveValidReviewRequest() throws Exception {

        // given
        ReviewSaveRequest request = new ReviewSaveRequest(0L, "restaurantId", (byte) 3, "comment");

        String requestAsString = objectMapper.writeValueAsString(request);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/reviews")
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void shouldNotSaveWhenReviewServiceThrowsIOException() throws Exception {

        // Setup
        when(mockReviewService.save(new ReviewSaveRequest(0L, "restaurantId", (byte) 3, "comment")))
                .thenThrow(IOException.class);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/reviews")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void shouldGetReviewById() throws Exception {

        // given
        ReviewResponse reviewResponse = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        // when
        when(mockReviewService.getById(0L)).thenReturn(reviewResponse);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/reviews/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllReviews() throws Exception {

        // given
        List<ReviewResponse> reviewResponses = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewService.getAll()).thenReturn(reviewResponses);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/reviews")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldNotGetAllWhenReviewServiceReturnsNoItems() throws Exception {

        // when
        when(mockReviewService.getAll()).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/reviews")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllReviewsByUserId() throws Exception {

        // given
        List<ReviewResponse> reviewResponses = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewService.getAllByUserId(0L)).thenReturn(reviewResponses);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/reviews/with-userId/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllReviewsByUserIdWhenReviewServiceReturnsNoItems() throws Exception {

        // when
        when(mockReviewService.getAllByUserId(0L)).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/reviews/with-userId/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllReviewsByRestaurantId() throws Exception {

        // given
        List<ReviewResponse> reviewResponses = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewService.getAllByRestaurantId("restaurantId")).thenReturn(reviewResponses);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/reviews/with-restaurantId/{restaurantId}", "restaurantId")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllReviewsByRestaurantIdWhenReviewServiceReturnsNoItems() throws Exception {

        // when
        when(mockReviewService.getAllByRestaurantId("restaurantId")).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/reviews/with-restaurantId/{restaurantId}", "restaurantId")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldUpdateReview() throws Exception {

        // given
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, (byte) 1, "comment");

        String requestAsString = objectMapper.writeValueAsString(request);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/reviews/{id}", 0)
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeleteReview() throws Exception {

        // given
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/reviews/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockReviewService).delete(0L);
    }
}
