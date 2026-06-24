package ru.ildar.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Column
    private ApplicationStatus status;

    @Column
    private UUID userId;

    public Application() {
    }

    public Application(long id, String title, String description, Date createdAt, Date updatedAt,
                       Date startAt, Date endAt, Date expirationAt, ApplicationStatus status,
                       UUID userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.expirationAt = expirationAt;
        this.status = status;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Date getExpirationAt() {
        return expirationAt;
    }

    public void setExpirationAt(Date expirationAt) {
        this.expirationAt = expirationAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(startAt, that.startAt) && Objects.equals(endAt, that.endAt) && Objects.equals(expirationAt, that.expirationAt) && status == that.status && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        result = 31 * result + Objects.hashCode(startAt);
        result = 31 * result + Objects.hashCode(endAt);
        result = 31 * result + Objects.hashCode(expirationAt);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(userId);
        return result;
    }
}
