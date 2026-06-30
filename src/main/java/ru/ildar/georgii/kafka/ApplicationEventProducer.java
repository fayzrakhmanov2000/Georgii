package ru.ildar.georgii.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ildar.georgii.dto.StatusChangedEvent;
import ru.ildar.georgii.entity.ApplicationStatus;

import java.time.Instant;

@Service
public class ApplicationEventProducer {

    private final KafkaTemplate<String, StatusChangedEvent> kafkaTemplate;
    private static final String TOPIC_NAME = "order-status-events";

    public ApplicationEventProducer(KafkaTemplate<String, StatusChangedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStatusChange(Long applicationId, ApplicationStatus newStatus) {
        StatusChangedEvent event = new StatusChangedEvent(
                applicationId,
                newStatus,
                Instant.now().toString());

        kafkaTemplate.send(TOPIC_NAME, String.valueOf(applicationId), event);
    }

}
