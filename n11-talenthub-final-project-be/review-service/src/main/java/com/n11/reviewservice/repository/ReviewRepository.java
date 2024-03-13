package com.n11.reviewservice.repository;

import com.n11.reviewservice.entity.Review;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT AVG(rate) AS average_rate\n" +
            "FROM reviews r \n" +
            "WHERE r.restaurant_id  = :restaurantId", nativeQuery = true)
    double calculateAverageRateOfRestaurant(@Param(value = "restaurantId") String restaurantId);

    List<Review> findAllByUserId(Long userId);

    List<Review> findAllByRestaurantId(String restaurantId);
}
