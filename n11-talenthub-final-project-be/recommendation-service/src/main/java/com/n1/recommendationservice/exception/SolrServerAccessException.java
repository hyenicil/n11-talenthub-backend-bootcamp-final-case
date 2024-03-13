package com.n1.recommendationservice.exception;

import com.n1.recommendationservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class SolrServerAccessException extends RuntimeException {
    public SolrServerAccessException(ErrorMessages message) {
        super(message.getMessage());
    }
}
