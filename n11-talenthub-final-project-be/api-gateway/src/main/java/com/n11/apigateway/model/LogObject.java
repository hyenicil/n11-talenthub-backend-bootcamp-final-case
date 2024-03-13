package com.n11.apigateway.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Mehmet Akif Tanisik
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogObject {
    private LocalDateTime logTime;
    private String method;
    private String requestUri;
    private String requestBodyString;
    private int responseStatusCode;
    private String responseBodyString;
    private String contentType;
    private String sourceApi;
    private int responseLatency;

}
