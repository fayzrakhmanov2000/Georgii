package ru.ildar.georgii.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.entity.ApplicationStatus;

import java.util.List;

public interface Repository extends JpaRepository<Application, Long> {
    List<Application> getApplicationsByStatusNotIn(List<ApplicationStatus> expired);
}
