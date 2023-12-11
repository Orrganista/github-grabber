package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.repository.InvalidRepositoryParameter;
import com.orrganista.githubgrabber.exception.repository.NoSuchRepositoryException;
import com.orrganista.githubgrabber.exception.repository.RepositoryAlreadyExistsException;
import com.orrganista.githubgrabber.mapper.RepositoryMapper;
import com.orrganista.githubgrabber.model.dto.CreateRepositoryDto;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.model.dto.UpdateRepositoryDto;
import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.model.entity.RepositoryId;
import com.orrganista.githubgrabber.repository.RepositoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final RepositoryMapper repositoryMapper;


    public RepositoryResponseDto createUserRepository(String owner, String repo, CreateRepositoryDto createRepositoryDto) {
        if (repositoryRepository.existsById(new RepositoryId(owner, repo))) {
            throw new RepositoryAlreadyExistsException(owner, repo);
        }

        if (createRepositoryDto.getStars() < 0) {
            throw new InvalidRepositoryParameter("stars", createRepositoryDto.getStars().toString());
        }

        Repository repository = repositoryMapper.toRepository(createRepositoryDto);
        repository.setId(new RepositoryId(owner, repo));
        Repository createdRepository = repositoryRepository.save(repository);
        log.info("Created repository {}.", createdRepository.getId());
        return repositoryMapper.toRepositoryResponseDto(createdRepository);
    }

    public RepositoryResponseDto getUserRepositoryByName(String owner, String repo) {
        Repository repository = repositoryRepository.findById(new RepositoryId(owner, repo))
                .orElseThrow(() -> new NoSuchRepositoryException(owner, repo));
        log.info("Retrieved repository {}.", repository.getId());
        return repositoryMapper.toRepositoryResponseDto(repository);
    }

    public RepositoryResponseDto putUserRepositoryByName(String owner, String repo, UpdateRepositoryDto updateRepositoryDto) {
        Repository repository = repositoryRepository.findById(new RepositoryId(owner, repo))
                .orElseThrow(() -> new NoSuchRepositoryException(owner, repo));

        repository.update(repositoryMapper.toRepository(updateRepositoryDto));
        Repository updatedRepository = repositoryRepository.save(repository);
        log.info("Updated repository {}.", updatedRepository.getId());
        return repositoryMapper.toRepositoryResponseDto(updatedRepository);
    }

    public void deleteLocalUserRepositoryByName(String owner, String repo) {
        Repository repository = repositoryRepository.findById(new RepositoryId(owner, repo))
                .orElseThrow(() -> new NoSuchRepositoryException(owner, repo));
        repositoryRepository.delete(repository);
        log.info("Deleted repository {}.", repository.getId());
    }
}
