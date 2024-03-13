package com.n11.restaurantservice.repository;

import com.n11.restaurantservice.document.Restaurant;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * @author Mehmet Akif Tanisik
 */
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {
}
