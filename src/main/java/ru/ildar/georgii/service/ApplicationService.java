package ru.ildar.georgii.service;

import org.springframework.stereotype.Service;
import ru.ildar.georgii.dto.ApplicationDTO;
import ru.ildar.georgii.dto.ApplicationRequestDTO;
import ru.ildar.georgii.dto.ApplicationResponseDTO;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.entity.ApplicationEvent;
import ru.ildar.georgii.entity.ApplicationStatus;
import ru.ildar.georgii.repo.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final Repository repository;

    private final StateMachineService stateMachineService;

    public ApplicationService (Repository repository, StateMachineService stateMachineService) {
        this.repository = repository;
        this.stateMachineService = stateMachineService;
    }

    public Application assign(Long id) {

        return stateMachineService.processOrderPayment(id, ApplicationEvent.ASSIGN);

    }

    public Application begin(Long id) {

        return stateMachineService.processOrderPayment(id, ApplicationEvent.BEGIN);

    }

    public Application complete(Long id) {

        return stateMachineService.processOrderPayment(id, ApplicationEvent.COMPLETE);

    }

    public Application deleteState(Long id) {

        return stateMachineService.processOrderPayment(id, ApplicationEvent.DELETE);

    }

    public Application expire(Long id) {

        return stateMachineService.processOrderPayment(id, ApplicationEvent.EXPIRE);

    }

    public Application mapToEntity(ApplicationRequestDTO applicationRequestDTO){
        Application application = new Application();
        application.setTitle(applicationRequestDTO.getTitle());
        application.setDescription(applicationRequestDTO.getDescription());
        application.setStartAt(applicationRequestDTO.getStartAt());
        application.setEndAt(applicationRequestDTO.getEndAt());
        application.setExpirationAt(applicationRequestDTO.getExpirationAt());
        application.setStatus(applicationRequestDTO.getStatus());
        return application;
    }

    private ApplicationResponseDTO mapToResponse(Application application) {
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        applicationResponseDTO.setId(application.getId());
        applicationResponseDTO.setTitle(application.getTitle());
        applicationResponseDTO.setDescription(application.getDescription());
        applicationResponseDTO.setCreatedAt(application.getCreatedAt());
        applicationResponseDTO.setUpdatedAt(application.getUpdatedAt());
        applicationResponseDTO.setStartAt(application.getStartAt());
        applicationResponseDTO.setEndAt(application.getEndAt());
        applicationResponseDTO.setExpirationAt(application.getExpirationAt());
        applicationResponseDTO.setStatus(application.getStatus());
        applicationResponseDTO.setUserId(application.getUserId());
        return applicationResponseDTO;
    }

    public List<ApplicationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(application -> mapToResponse(application))
                .toList();
    }

    public Optional<ApplicationResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(application -> mapToResponse(application));
    }

    public ApplicationResponseDTO save(ApplicationRequestDTO request) {
        Application application = mapToEntity(request);
        Date now = new Date();
        application.setCreatedAt(now);
        application.setUpdatedAt(now);
        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.CREATED);
        }
        Application saved = repository.save(application);
        return mapToResponse(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<ApplicationResponseDTO> update(Long id, ApplicationRequestDTO applicationRequestDTO) {
        return repository.findById(id)
                .map(application -> {
                    application.setTitle(applicationRequestDTO.getTitle());
                    application.setDescription(applicationRequestDTO.getDescription());
                    application.setStartAt(applicationRequestDTO.getStartAt());
                    application.setEndAt(applicationRequestDTO.getEndAt());
                    application.setExpirationAt(applicationRequestDTO.getExpirationAt());
                    application.setStatus(applicationRequestDTO.getStatus());
                    application.setUpdatedAt(new Date());
                    Application saved = repository.save(application);
                    return mapToResponse(saved);
                });
    }
}
