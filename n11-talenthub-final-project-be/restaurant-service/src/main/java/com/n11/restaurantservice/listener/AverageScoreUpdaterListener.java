package com.n11.restaurantservice.listener;

import com.n11.restaurantservice.document.Restaurant;
import com.n11.restaurantservice.dto.ScoreUpdateMessage;
import com.n11.restaurantservice.exception.ScoreUpdaterQueueException;
import com.n11.restaurantservice.exception.RestaurantNotFoundException;
import com.n11.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.n11.restaurantservice.common.error.ErrorMessages.NEGATIVE_AVERAGE_SCORE_EXCEPTION;
import static com.n11.restaurantservice.common.error.ErrorMessages.RESTAURANT_NOT_FOUND;

/**
 * @author Mehmet Akif Tanisik
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class AverageScoreUpdaterListener {

    private final RestaurantRepository restaurantRepository;

    @RabbitListener(queues = "score.updater.queue")
    public void process(ScoreUpdateMessage message) {

                Restaurant restaurant = restaurantRepository.findById(message.restaurantId())
                        .orElseThrow(() -> new ScoreUpdaterQueueException(RESTAURANT_NOT_FOUND));

                if (message.averageScore() < 0 || message.averageScore() > 5) {
                    throw new ScoreUpdaterQueueException(NEGATIVE_AVERAGE_SCORE_EXCEPTION);
                }

                restaurant.setAverageScore(message.averageScore());

                restaurantRepository.save(restaurant);

    }

}
