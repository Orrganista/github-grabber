package com.orrganista.githubgrabber.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryId implements Serializable {

    private String ownerName;
    private String repositoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepositoryId other)) return false;
        return ownerName.equals(other.getOwnerName()) &&
                repositoryName.equals(other.getRepositoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwnerName(), getRepositoryName());
    }

    @Override
    public String toString() {
        return String.format("%s/%s", ownerName, repositoryName);
    }
}
