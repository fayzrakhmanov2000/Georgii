package ru.ildar.georgii.service;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.entity.ApplicationEvent;
import ru.ildar.georgii.entity.ApplicationStatus;
import ru.ildar.georgii.repo.Repository;

import static ru.ildar.georgii.entity.ApplicationEvent.CREATE;

@Service
public class StateMachineService {

    private final Repository repository;
    private final StateMachineFactory<ApplicationStatus, ApplicationEvent> stateMachineFactory;

    public StateMachineService(Repository repository,
                               StateMachineFactory<ApplicationStatus, ApplicationEvent> stateMachineFactory) {
        this.repository = repository;
        this.stateMachineFactory = stateMachineFactory;
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
        } else {
            throw new IllegalStateException("Невозможный переход");
        }
        return application;
    }
}
