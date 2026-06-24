package ru.ildar.georgii.statemachine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import ru.ildar.georgii.entity.ApplicationEvent;
import ru.ildar.georgii.entity.ApplicationStatus;

import static ru.ildar.georgii.entity.ApplicationEvent.*;
import static ru.ildar.georgii.entity.ApplicationStatus.*;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<ApplicationStatus, ApplicationEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<ApplicationStatus, ApplicationEvent> states) throws Exception {
        states.withStates()
                .initial(CREATED)
                .state(ASSIGNED)
                .state(BEGAN)
                .state(COMPLETED)
                .state(DELETED)
                .state(EXPIRED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApplicationStatus, ApplicationEvent> transitions) throws Exception {
        transitions
                .withExternal().source(CREATED).target(ASSIGNED).event(ASSIGN)
                .and()
                .withExternal().source(ASSIGNED).target(BEGAN).event(BEGIN)
                .and()
                .withExternal().source(BEGAN).target(COMPLETED).event(COMPLETE)
                .and()
                .withExternal().source(COMPLETED).target(DELETED).event(DELETE)
                .and()
                .withExternal().source(DELETED).target(EXPIRED).event(EXPIRE);
    }
}

