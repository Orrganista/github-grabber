package com.orrganista.githubgrabber.repository;

import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.model.entity.RepositoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, RepositoryId> {

    boolean existsById(RepositoryId id);
}
