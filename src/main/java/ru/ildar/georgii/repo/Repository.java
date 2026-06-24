package ru.ildar.georgii.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ildar.georgii.entity.Application;

public interface Repository extends JpaRepository<Application, Long> {
}
