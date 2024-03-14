package com.n1.recommendationservice.service;

import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.exception.SolrIOException;
import com.n1.recommendationservice.exception.SolrServerAccessException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.n1.recommendationservice.common.error.ErrorMessages.SOLR_IO_EXCEPTION_MESSAGE;
import static com.n1.recommendationservice.common.error.ErrorMessages.SOLR_SERVER_EXCEPTION_MESSAGE;

/**
 * @author Mehmet Akif Tanisik
 */
@Component
public class SolrClientService {

    public List<RestaurantResponse> getRestaurantsFromSolr(String userLocation) {

        List<RestaurantResponse> responseList = new ArrayList<>();

        try (HttpSolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/restaurants").build()) {

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

                String id = (String) document.getFirstValue("id");
                String name = (String) document.getFirstValue("name");
                String location = (String) document.getFirstValue("location");
                Double averageScore = (Double) document.getFirstValue("averageScore");

                responseList.add( new RestaurantResponse(id, name, location, averageScore));
            }
        } catch (SolrServerException e) {
            throw new SolrServerAccessException(SOLR_SERVER_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new SolrIOException(SOLR_IO_EXCEPTION_MESSAGE);
        }

        return responseList;
        }
}
