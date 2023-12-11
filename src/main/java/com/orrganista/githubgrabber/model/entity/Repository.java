package com.orrganista.githubgrabber.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Repository")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

    @EmbeddedId
    private RepositoryId id;
    private String description;
    @Column(nullable = false)
    private String cloneUrl;
    @Column(nullable = false)
    private Integer stars;
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public void update(Repository repository) {
        description = repository.getDescription() != null ? repository.description : description;
        cloneUrl = repository.getCloneUrl() != null ? repository.cloneUrl : cloneUrl;
        stars = repository.getStars() != null ? repository.stars : stars;
        createdAt = repository.getCreatedAt() != null ? repository.createdAt : createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Repository))
            return false;

        Repository other = (Repository) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Repository [fullName=%s, description=%s, cloneUrl=%s, stars=%d, createdAt=%s]",
                id, description, cloneUrl, stars, createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"))
        );
    }
}
