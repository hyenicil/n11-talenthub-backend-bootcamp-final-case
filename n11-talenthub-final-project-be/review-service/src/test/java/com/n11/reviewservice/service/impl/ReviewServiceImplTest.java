package com.n11.reviewservice.service.impl;

import com.n11.reviewservice.client.RestaurantClient;
import com.n11.reviewservice.client.UserClient;
import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;
import com.n11.reviewservice.dto.ScoreUpdateMessage;
import com.n11.reviewservice.entity.Review;
import com.n11.reviewservice.exception.EntityNotFoundException;
import com.n11.reviewservice.mapper.ReviewMapper;
import com.n11.reviewservice.repository.ReviewRepository;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository mockReviewRepository;
    @Mock
    private ReviewMapper mockReviewMapper;
    @Mock
    private UserClient mockUserClient;
    @Mock
    private RestaurantClient mockRestaurantClient;
    @Mock
    private RabbitTemplate mockRabbitTemplate;

    private ReviewServiceImpl reviewServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        reviewServiceImplUnderTest = new ReviewServiceImpl(mockReviewRepository, mockReviewMapper, mockUserClient,
                mockRestaurantClient, mockRabbitTemplate);
    }

    @Test
    void shouldSaveReview() throws Exception {

        // given
        ReviewSaveRequest request = new ReviewSaveRequest(0L, "restaurantId", (byte) 0b0, "comment");

        ReviewResponse expectedResult = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Review review = new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Response userResponse = mock(Response.class);

        Response restaurantResponse = mock(Response.class);

        Review review1 = new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        ReviewResponse reviewResponse = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        // when
        when(mockReviewMapper.mapReviewSaveRequestToReview(request)).thenReturn(review);

        when(userResponse.status()).thenReturn(200);

        when(restaurantResponse.status()).thenReturn(200);

        when(mockUserClient.getUserById(0L)).thenReturn(userResponse);

        when(mockRestaurantClient.getRestaurantById("restaurantId")).thenReturn(restaurantResponse);

        when(mockReviewRepository.save(any(Review.class))).thenReturn(review1);

        when(mockReviewMapper.mapReviewToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.save(request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldNotSaveWhenUserNoFound() {

        // given
        ReviewSaveRequest request = new ReviewSaveRequest(0L, "restaurantId", (byte) 0b0, "comment");

        Review review = new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Response userResponse = mock(Response.class);

        // when
        when(mockReviewMapper.mapReviewSaveRequestToReview(request)).thenReturn(review);

        when(userResponse.status()).thenReturn(404);

        when(mockUserClient.getUserById(0L)).thenReturn(userResponse);

        // then
        assertThrows(EntityNotFoundException.class, () -> reviewServiceImplUnderTest.save(request));
    }

    @Test
    void shouldNotSaveWhenRestaurantNotFound() {

        // given
        ReviewSaveRequest request = new ReviewSaveRequest(0L, "restaurantId", (byte) 0b0, "comment");

        Review review = new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Response userResponse = mock(Response.class);

        Response restaurantResponse = mock(Response.class);

        // when
        when(mockReviewMapper.mapReviewSaveRequestToReview(request)).thenReturn(review);

        when(userResponse.status()).thenReturn(200);

        when(restaurantResponse.status()).thenReturn(404);

        when(mockUserClient.getUserById(0L)).thenReturn(userResponse);

        when(mockRestaurantClient.getRestaurantById("restaurantId")).thenReturn(restaurantResponse);

        // then
        assertThrows(EntityNotFoundException.class, () -> reviewServiceImplUnderTest.save(request));
    }

    @Test
    void shouldGetReviewById() {

        // given
        ReviewResponse expectedResult = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Optional<Review> review = Optional.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        ReviewResponse reviewResponse = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        // when
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        when(mockReviewMapper.mapReviewToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.getById(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllReviews() {

        // given
        List<ReviewResponse> expectedResult = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        List<Review> reviews = List.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewRepository.findAll()).thenReturn(reviews);

        when(mockReviewMapper.mapReviewListToReviewResponseList(reviews)).thenReturn(expectedResult);

        List<ReviewResponse> result = reviewServiceImplUnderTest.getAll();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateReview() {

        // given
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, (byte) 0b0, "comment");

        ReviewResponse expectedResult = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        Optional<Review> review = Optional.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        Review review1 = new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        ReviewResponse reviewResponse = new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment");

        // when
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        when(mockReviewMapper.mapReviewUpdateRequestToReview(any(Review.class),
                eq(new ReviewUpdateRequest(0L, (byte) 0b0, "comment")))).thenReturn(review1);

        when(mockReviewMapper.mapReviewToReviewResponse(any(Review.class))).thenReturn(reviewResponse);

        ReviewResponse result = reviewServiceImplUnderTest.update(0L, request);

        // then
        assertThat(result).isEqualTo(expectedResult);

        verify(mockReviewRepository).save(any(Review.class));
    }

    @Test
    void shouldNotUpdateScoreIfRateIsNotChanged() {

        // given
        ReviewUpdateRequest request = new ReviewUpdateRequest(0L, (byte) 2, "comment");

        ReviewResponse expectedResult = new ReviewResponse(0L, 0L, "restaurantId", (byte) 2, "comment");

        Review review = new Review(0L, 0L, "restaurantId", (byte) 5, "comment");

        Review review1 = new Review(0L, 0L, "restaurantId", (byte) 2, "comment");

        // when
        when(mockReviewRepository.findById(0L)).thenReturn(Optional.of(review));

        when(mockReviewMapper.mapReviewUpdateRequestToReview(review, request)).thenReturn(review1);

        when(mockReviewMapper.mapReviewToReviewResponse(any(Review.class))).thenReturn(expectedResult);

        ReviewResponse result = reviewServiceImplUnderTest.update(0L, request);

        // then
        assertThat(result).isEqualTo(expectedResult);

        verify(mockReviewRepository).save(any(Review.class));
    }

    @Test
    void shouldDeleteReview() {

        // given
        Optional<Review> review = Optional.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        List<Review> reviews = List.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewRepository.findById(0L)).thenReturn(review);

        when(mockReviewRepository.findAllByRestaurantId("restaurantId")).thenReturn(reviews);

        when(mockReviewRepository.calculateAverageRateOfRestaurant("restaurantId")).thenReturn(0.0);

        reviewServiceImplUnderTest.delete(0L);

        // then
        verify(mockReviewRepository).delete(any(Review.class));

        verify(mockRabbitTemplate).convertAndSend("score.updater.queue.exchange", "score.updater.queue.routing-key",
                ScoreUpdateMessage.builder()
                        .averageScore(0.0)
                        .restaurantId("restaurantId")
                        .build());
    }

    @Test
    void shouldGetAllReviewsByUserId() {

        // given
        List<ReviewResponse> expectedResult = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        List<Review> reviews = List.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewRepository.findAllByUserId(0L)).thenReturn(reviews);

        when(mockReviewMapper.mapReviewListToReviewResponseList(reviews)).thenReturn(expectedResult);

        List<ReviewResponse> result = reviewServiceImplUnderTest.getAllByUserId(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllReviewsByRestaurantId() {

        // given
        List<ReviewResponse> expectedResult = List.of(
                new ReviewResponse(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        List<Review> reviews = List.of(new Review(0L, 0L, "restaurantId", (byte) 0b0, "comment"));

        // when
        when(mockReviewRepository.findAllByRestaurantId("restaurantId")).thenReturn(reviews);

        when(mockReviewMapper.mapReviewListToReviewResponseList(reviews)).thenReturn(expectedResult);

        List<ReviewResponse> result = reviewServiceImplUnderTest.getAllByRestaurantId("restaurantId");

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
