package com.n11.loggingservice.listener;

import com.n11.loggingservice.exception.UnProcessableLogException;
import com.n11.loggingservice.model.LogObject;
import com.n11.loggingservice.repository.LobObjectRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mehmet Akif Tanisik
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingListener {

    private final LobObjectRepository lobObjectRepository;

    @RabbitListener(queues = "gateway.logging.queue")
    public void process(LogObject message) {
        try {
            lobObjectRepository.save(message);
        } catch (Exception e) {
            log.info("Error occurred during saving log into database. Cause : {}",e.getMessage());
            throw new UnProcessableLogException("Unprocessable log exception occurred!");
        }
    }

}
