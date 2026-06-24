package ru.ildar.georgii.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ildar.georgii.dto.ApplicationRequestDTO;
import ru.ildar.georgii.dto.ApplicationResponseDTO;
import ru.ildar.georgii.entity.Application;
import ru.ildar.georgii.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class Controller {

    private final ApplicationService applicationService;

    public Controller(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationResponseDTO> getAllApplications() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long id) {
        return applicationService.findById(id)
                .map(applicationResponseDTO -> ResponseEntity.ok(applicationResponseDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createProduct(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.save(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateProduct(@PathVariable Long id,
                                                                @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        return applicationService.update(id, applicationRequestDTO)
                .map(applicationResponseDTO -> ResponseEntity.ok(applicationResponseDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (applicationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assign")
    public Application assign(@PathVariable Long id) {
        return applicationService.assign(id);
    }

    @PostMapping("/{id}/begin")
    public Application begin(@PathVariable Long id) {
        return applicationService.begin(id);
    }

    @PostMapping("/{id}/complete")
    public Application complete(@PathVariable Long id) {
        return applicationService.complete(id);
    }

    @PostMapping("/{id}/delete")
    public Application delete(@PathVariable Long id) {
        return applicationService.deleteState(id);
    }

    @PostMapping("/{id}/expire")
    public Application expire(@PathVariable Long id) {
        return applicationService.expire(id);
    }
}
