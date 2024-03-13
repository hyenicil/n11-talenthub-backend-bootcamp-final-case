package com.n11.restaurantservice.document;

import com.n11.restaurantservice.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.UUID;

/**
 * @author Mehmet Akif Tanisik
 */
@Getter
@Setter
@AllArgsConstructor
@SolrDocument(collection = "restaurants")
public class Restaurant extends BaseEntity {
    @Id
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String location;
    @Field
    private Double averageScore;
    @Field
    private Boolean isActive;


    public Restaurant() {
        this.id = generateUniqueId();
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
