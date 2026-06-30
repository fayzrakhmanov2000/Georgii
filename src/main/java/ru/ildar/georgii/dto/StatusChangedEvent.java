package ru.ildar.georgii.dto;

import ru.ildar.georgii.entity.ApplicationStatus;

public record StatusChangedEvent(
        Long applicationId,
        ApplicationStatus newStatus,
        String changedAt
) {}