package ru.ildar.georgii.dto;

import lombok.Data;
import ru.ildar.georgii.entity.ApplicationStatus;

import java.util.Date;
import java.util.UUID;

@Data
public class ApplicationResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Date startAt;
    private Date endAt;
    private Date expirationAt;
    private ApplicationStatus status;
    private UUID userId;
}
