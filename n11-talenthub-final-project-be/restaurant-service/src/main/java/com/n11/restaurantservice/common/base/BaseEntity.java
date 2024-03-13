package com.n11.restaurantservice.common.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.time.LocalDateTime;

/**
 * @author Mehmet Akif Tanisik
 */

@Getter
@Setter
public abstract class BaseEntity{
    @Field
    private LocalDateTime createdAt;
    @Field
    private LocalDateTime updatedAt;
    @Field
    private Long createdBy;
    @Field
    private Long updatedBy;
}
