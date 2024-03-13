package com.n11.loggingservice.repository;

import com.n11.loggingservice.model.LogObject;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Mehmet Akif Tanisik
 */
public interface LobObjectRepository extends MongoRepository<LogObject, String> {
}
