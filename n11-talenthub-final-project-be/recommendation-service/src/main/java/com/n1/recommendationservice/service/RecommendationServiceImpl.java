package com.n1.recommendationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n1.recommendationservice.client.AddressClient;
import com.n1.recommendationservice.dto.AddressResponse;
import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.exception.AddressNotFoundException;
import com.n1.recommendationservice.exception.SolrIOException;
import com.n1.recommendationservice.exception.SolrServerAccessException;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.n1.recommendationservice.common.error.ErrorMessages.*;

/**
 * @author Mehmet Akif Tanisik
 */

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final AddressClient addressClient;

    public List<RestaurantResponse> getRecommendedRestaurants(Long userId) {

        List<RestaurantResponse> responseList = new ArrayList<>();
        try (HttpSolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/restaurants").build()) {

            var responseEntity = addressClient.getByUserId(userId);
            var baseRestResponse = responseEntity.getBody();
            String userLocation;

            if (baseRestResponse != null) {
                List<AddressResponse> addressList = baseRestResponse.getData();
                if (addressList.isEmpty()) {
                    throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
                }
                userLocation = addressList.get(0).location();
            } else {
                throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
            }


            final Map<String, String> queryParamMap = new HashMap<>();
            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "fq={!geofilt pt=" + userLocation + " sfield=location d=10}");
            queryParamMap.put("start", "0");
            queryParamMap.put("rows", "3");
            queryParamMap.put("sort", "sum(mul(averageScore,14),mul(sub(10,geodist(" + userLocation + ",location)),3)) desc");

            MapSolrParams queryParams = new MapSolrParams(queryParamMap);

            QueryResponse response = solrClient.query(queryParams);

            SolrDocumentList documents = response.getResults();
            for (SolrDocument document : documents) {

                RestaurantResponse restaurantResponse = mapSolrDocumentToRestaurantResponse(document);

                responseList.add(restaurantResponse);
            }
        } catch (AddressNotFoundException e) {
            throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (SolrServerException e) {
            throw new SolrServerAccessException(SOLR_SERVER_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new SolrIOException(SOLR_IO_EXCEPTION_MESSAGE);
        }

        return responseList;
    }

    private RestaurantResponse mapSolrDocumentToRestaurantResponse(SolrDocument document) {

        String id = (String) document.getFirstValue("id");
        String name = (String) document.getFirstValue("name");
        String location = (String) document.getFirstValue("location");
        Double averageScore = (Double) document.getFirstValue("averageScore");
        return new RestaurantResponse(id, name, location, averageScore);
    }
}
