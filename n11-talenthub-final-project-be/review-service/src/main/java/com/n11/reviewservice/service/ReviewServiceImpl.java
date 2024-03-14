package com.n11.reviewservice.service;

import com.n11.reviewservice.client.RestaurantClient;
import com.n11.reviewservice.client.UserClient;
import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;
import com.n11.reviewservice.dto.ScoreUpdateMessage;
import com.n11.reviewservice.entity.Review;
import com.n11.reviewservice.exception.EntityNotFoundException;
import com.n11.reviewservice.exception.RestaurantNotFoundException;
import com.n11.reviewservice.exception.ReviewNotFoundException;
import com.n11.reviewservice.exception.UserNotFoundException;
import com.n11.reviewservice.mapper.ReviewMapper;
import com.n11.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.n11.reviewservice.common.error.ErrorMessages.*;


/**
 * @author Mehmet Akif Tanisik
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserClient userClient;
    private final RestaurantClient restaurantClient;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public ReviewResponse save(ReviewSaveRequest request) throws IOException {

        Review review = reviewMapper.mapReviewSaveRequestToReview(request);

        try {
            var user = userClient.getUserById(request.userId());
            var restaurant = restaurantClient.getRestaurantById(request.restaurantId());


            if (user.status()!=200) {
                throw new UserNotFoundException(USER_NOT_FOUND);
            }
            if (restaurant.status()!=200) {
                throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND);
            }

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }

        Review savedReview = reviewRepository.save(review);

        addScoreUpdateMsgToQueue(review.getRestaurantId());

        return reviewMapper.mapReviewToReviewResponse(savedReview);
    }

    @Override
    public ReviewResponse getById(Long id) {

        Review review = getReviewById(id);

        return reviewMapper.mapReviewToReviewResponse(review);
    }

    @Override
    public List<ReviewResponse> getAll() {

        List<Review> reviewList = reviewRepository.findAll();

        return reviewMapper.mapReviewListToReviewResponseList(reviewList);
    }

    @Override
    public ReviewResponse update(Long id, ReviewUpdateRequest request) {

        Review review = getReviewById(request.id());

        Review updatedReview = reviewMapper.mapReviewUpdateRequestToReview(review, request);
        reviewRepository.save(updatedReview);

        if (review.getRate() != request.rate()) {
            addScoreUpdateMsgToQueue(review.getRestaurantId());
        }

        return reviewMapper.mapReviewToReviewResponse(updatedReview);
    }

    @Override
    public void delete(Long id) {

        Review review = getReviewById(id);

        reviewRepository.delete(review);

        addScoreUpdateMsgToQueue(review.getRestaurantId());

    }

    @Override
    public List<ReviewResponse> getAllByUserId(Long userId) {

        List<Review> allReviewsByUserId = reviewRepository.findAllByUserId(userId);

        return reviewMapper.mapReviewListToReviewResponseList(allReviewsByUserId);
    }

    @Override
    public List<ReviewResponse> getAllByRestaurantId(String restaurantId) {

        List<Review> allReviewsByRestaurantId = reviewRepository.findAllByRestaurantId(restaurantId);

        return reviewMapper.mapReviewListToReviewResponseList(allReviewsByRestaurantId);

    }

    public void addScoreUpdateMsgToQueue(String restaurantId) {

        if (reviewRepository.findAllByRestaurantId(restaurantId).isEmpty()) {
            return;
        }

        double averageScore = reviewRepository.calculateAverageRateOfRestaurant(restaurantId);

        ScoreUpdateMessage scoreUpdateMessage = ScoreUpdateMessage.builder()
                .averageScore(averageScore)
                .restaurantId(restaurantId)
                .build();

        rabbitTemplate.convertAndSend("score.updater.queue.exchange", "score.updater.queue.routing-key", scoreUpdateMessage);
    }

    private Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND));
    }
}
