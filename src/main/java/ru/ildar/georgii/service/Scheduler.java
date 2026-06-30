package ru.ildar.georgii.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.entity.ApplicationStatus;
import ru.ildar.georgii.repo.Repository;

import java.util.List;

@Component
@EnableScheduling
public class Scheduler {

    private StateMachineService stateMachineService;
    private Repository repository;

    public Scheduler(StateMachineService stateMachineService, Repository repository) {
        this.stateMachineService = stateMachineService;
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 60000)
    public void scheduleFixedDelayTask() {
        List<Application> foundApplications =
                repository.getApplicationsByStatusNotIn(List.of(
                        ApplicationStatus.EXPIRED,
                        ApplicationStatus.COMPLETED,
                        ApplicationStatus.DELETED));
        for (Application application : foundApplications) {
            stateMachineService.shedulerStatusChange(application);
        }
    }
}
