package ru.ildar.georgii.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Date startAt;

    @Column
    private Date endAt;

    @Column
    private Date expirationAt;

    @Enumerated(EnumType.STRING)
    @Column
    private ApplicationStatus status;

    @Column
    private UUID userId;

}
