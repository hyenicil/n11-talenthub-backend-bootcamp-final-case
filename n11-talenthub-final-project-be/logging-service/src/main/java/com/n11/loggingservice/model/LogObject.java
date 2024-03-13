package com.n11.loggingservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "logs")
public class LogObject {
    @Id
    private String id;
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
