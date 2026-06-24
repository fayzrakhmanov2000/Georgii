package ru.ildar.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ildar.entity.Application;
import ru.ildar.repo.Repository;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class Controller {

    private final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Application> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return repository.findById(id)
                .map(application -> ResponseEntity.ok(application))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createProduct(@RequestBody Application application) {
        Application savedApplication = repository.save(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateProduct(@PathVariable Long id,
                                                     @RequestBody Application applicationDetails) {
        return repository.findById(id)
                .map(existingApplication -> {
                    existingApplication.setId(applicationDetails.getId());
                    existingApplication.setTitle(applicationDetails.getTitle());
                    existingApplication.setDescription(applicationDetails.getDescription());
                    existingApplication.setCreatedAt(applicationDetails.getCreatedAt());
                    existingApplication.setUpdatedAt(applicationDetails.getUpdatedAt());
                    existingApplication.setStartAt(applicationDetails.getStartAt());
                    existingApplication.setEndAt(applicationDetails.getEndAt());
                    existingApplication.setExpirationAt(applicationDetails.getExpirationAt());
                    existingApplication.setStatus(applicationDetails.getStatus());
                    existingApplication.setUserId(applicationDetails.getUserId());
                    Application updatedApplication = repository.save(existingApplication);
                    return ResponseEntity.ok(updatedApplication);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return repository.findById(id)
                .map(application -> {
                    repository.delete(application);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
