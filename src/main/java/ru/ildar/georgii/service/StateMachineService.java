package ru.ildar.georgii.service;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.entity.ApplicationEvent;
import ru.ildar.georgii.entity.ApplicationStatus;
import ru.ildar.georgii.kafka.ApplicationEventProducer;
import ru.ildar.georgii.repo.Repository;

import java.time.Instant;

@Service
public class StateMachineService {

    private final Repository repository;
    private final StateMachineFactory<ApplicationStatus, ApplicationEvent> stateMachineFactory;
    private final ApplicationEventProducer applicationEventProducer;

    public StateMachineService(Repository repository,
                               StateMachineFactory<ApplicationStatus, ApplicationEvent> stateMachineFactory,
                               ApplicationEventProducer applicationEventProducer) {
        this.repository = repository;
        this.stateMachineFactory = stateMachineFactory;
        this.applicationEventProducer = applicationEventProducer;
    }

    public Application processOrderPayment(Long id, ApplicationEvent event) {
        Application application = repository.findById(id).orElseThrow();

        StateMachine<ApplicationStatus, ApplicationEvent> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getStateMachineAccessor().doWithAllRegions(access -> access.resetStateMachine(
                new DefaultStateMachineContext<>(application.getStatus(), null, null, null, null)
        ));
        stateMachine.start();

        boolean isAccepted = stateMachine.sendEvent(
                MessageBuilder.withPayload(event)
                        .setHeader("applicationId", id)
                        .build()
        );

        if (isAccepted) {
            application.setStatus(stateMachine.getState().getId());
            repository.save(application);
            applicationEventProducer.sendStatusChange(id, application.getStatus());
        } else {
            throw new IllegalStateException("Невозможный переход");
        }
        return application;
    }

    public void shedulerStatusChange(Application application) {
        if (application.getExpirationAt() != null
                && application.getExpirationAt().toInstant().isBefore(Instant.now())) {
            processOrderPayment(application.getId(), ApplicationEvent.EXPIRE);
        }
    }
}
