package com.n1.recommendationservice.exception;

import com.n1.recommendationservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class SolrIOException extends RuntimeException{

    public SolrIOException(ErrorMessages message) {
        super(message.getMessage());
    }
}
