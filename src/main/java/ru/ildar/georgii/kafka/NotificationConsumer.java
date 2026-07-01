package ru.ildar.georgii.kafka;

import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.ildar.georgii.dto.NotificationEvent;
import ru.ildar.georgii.dto.StatusChangedEvent;

@Service
public class NotificationConsumer {

    private final JavaMailSender mailSender;

    public NotificationConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "order-status-events", groupId = "email-notification-group")
    public void listen(@Payload @Valid StatusChangedEvent event) {
        NotificationEvent notification = new NotificationEvent();
        notification.setRecipientEmail("some-user@example.com");
        notification.setSubject("Статус заявки изменён");
        notification.setMessage("Заявка #" + event.applicationId()
                + " перешла в статус " + event.newStatus());


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipientEmail());
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            message.setFrom("your-email@example.com");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
        }
    }
}
