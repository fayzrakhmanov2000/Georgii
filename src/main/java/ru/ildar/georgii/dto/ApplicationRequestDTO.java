package ru.ildar.georgii.dto;

import lombok.Data;
import ru.ildar.georgii.entity.ApplicationStatus;

import java.util.Date;
import java.util.UUID;

@Data
public class ApplicationRequestDTO {
    private String title;
    private String description;
    private Date startAt;
    private Date endAt;
    private Date expirationAt;
    private ApplicationStatus status;
}
