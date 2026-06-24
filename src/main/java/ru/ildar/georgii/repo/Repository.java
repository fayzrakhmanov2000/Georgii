package ru.ildar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ildar.entity.Application;

public interface Repository extends JpaRepository<Application, Long> {
}
