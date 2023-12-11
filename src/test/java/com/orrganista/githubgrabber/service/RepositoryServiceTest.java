package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.repository.InvalidRepositoryParameter;
import com.orrganista.githubgrabber.exception.repository.NoSuchRepositoryException;
import com.orrganista.githubgrabber.exception.repository.RepositoryAlreadyExistsException;
import com.orrganista.githubgrabber.mapper.RepositoryMapper;
import com.orrganista.githubgrabber.model.dto.CreateRepositoryDto;
import com.orrganista.githubgrabber.model.dto.UpdateRepositoryDto;
import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.model.entity.RepositoryId;
import com.orrganista.githubgrabber.repository.RepositoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import util.TestDataFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RepositoryServiceTest {

    private RepositoryRepository repositoryRepository;
    private RepositoryService repositoryService;
    private RepositoryMapper repositoryMapper;

    @BeforeEach
    public void setup() {
        repositoryRepository = Mockito.mock(RepositoryRepository.class);
        repositoryMapper = Mappers.getMapper(RepositoryMapper.class);
        repositoryService = new RepositoryService(repositoryRepository, repositoryMapper);
    }

    @Test
    void createUserRepository_RepositoryNotExists_RepositoryCreated() {
        var createRepositoryDto = TestDataFactory.getTestCreateRepositoryDto();
        var repository = repositoryMapper.toRepository(createRepositoryDto);
        repository.setId(new RepositoryId("owner", "repo"));
        var repositoryResponseDto = repositoryMapper.toRepositoryResponseDto(repository);
        when(repositoryRepository.save(any(Repository.class))).thenReturn(repository);

        var result = repositoryService.createUserRepository("owner", "repo", createRepositoryDto);

        assertNotNull(result);
        assertEquals(repositoryResponseDto.getFullName(), result.getFullName());
        assertEquals(repositoryResponseDto.getDescription(), result.getDescription());
        assertEquals(repositoryResponseDto.getCloneUrl(), result.getCloneUrl());
        assertEquals(repositoryResponseDto.getStars(), result.getStars());
        assertEquals(repositoryResponseDto.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void createUserRepository_RepositoryAlreadyExists_ExceptionThrown() {
        var createRepositoryDto = TestDataFactory.getTestCreateRepositoryDto();
        when(repositoryRepository.existsById(any(RepositoryId.class))).thenReturn(true);

        RepositoryAlreadyExistsException exception = Assertions.assertThrows(
                RepositoryAlreadyExistsException.class,
                () -> repositoryService.createUserRepository("owner", "repo", createRepositoryDto)
        );

        Assertions.assertEquals("Repository [repo] for user [owner] already exists.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void createUserRepository_StarsParameterIsInvalid_ExceptionThrown() {
        var createRepositoryDto = CreateRepositoryDto.builder().stars(-2).build();
        when(repositoryRepository.existsById(any(RepositoryId.class))).thenReturn(false);

        InvalidRepositoryParameter exception = Assertions.assertThrows(
                InvalidRepositoryParameter.class,
                () -> repositoryService.createUserRepository("owner", "repo", createRepositoryDto)
        );

        Assertions.assertEquals("Parameter [stars] is invalid for value [-2].", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void getUserRepositoryByName_RepositoryExists_RepositoryReturned() {
        var repository = TestDataFactory.getTestRepository();
        var repositoryResponseDto = repositoryMapper.toRepositoryResponseDto(repository);
        when(repositoryRepository.findById(repository.getId())).thenReturn(Optional.of(repository));

        var result = repositoryService.getUserRepositoryByName(repository.getId().getOwnerName(), repository.getId().getRepositoryName());

        assertNotNull(result);
        assertEquals(repositoryResponseDto.getFullName(), result.getFullName());
        assertEquals(repositoryResponseDto.getDescription(), result.getDescription());
        assertEquals(repositoryResponseDto.getCloneUrl(), result.getCloneUrl());
        assertEquals(repositoryResponseDto.getStars(), result.getStars());
        assertEquals(repositoryResponseDto.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void getUserRepositoryByName_RepositoryDoesNotExist_ExceptionThrown() {
        when(repositoryRepository.findById(any(RepositoryId.class))).thenReturn(Optional.empty());

        NoSuchRepositoryException exception = Assertions.assertThrows(
                NoSuchRepositoryException.class,
                () -> repositoryService.getUserRepositoryByName("owner", "repo")
        );

        Assertions.assertEquals("No repository with name [repo] found for the user [owner].", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void putUserRepositoryByName_RepositoryExists_RepositoryPut() {
        var repository = TestDataFactory.getTestRepository();
        when(repositoryRepository.findById(repository.getId())).thenReturn(Optional.of(repository));
        var updateRepositoryDto = TestDataFactory.getTestUpdateRepositoryDto();
        repository.update(repositoryMapper.toRepository(updateRepositoryDto));
        when(repositoryRepository.save(any(Repository.class))).thenReturn(repository);
        when(repositoryRepository.existsById(any(RepositoryId.class))).thenReturn(false);

        var result = repositoryService.putUserRepositoryByName(repository.getId().getOwnerName(), repository.getId().getRepositoryName(), updateRepositoryDto);

        assertNotNull(result);
        assertEquals(repository.getId().toString(), result.getFullName());
        assertEquals(updateRepositoryDto.getDescription(), result.getDescription());
        assertEquals(updateRepositoryDto.getCloneUrl(), result.getCloneUrl());
        assertEquals(updateRepositoryDto.getStars(), result.getStars());
        assertEquals(repository.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void putUserRepositoryByName_RepositoryDoesNotExist_ExceptionThrown() {
        when(repositoryRepository.findById(any(RepositoryId.class))).thenReturn(Optional.empty());

        NoSuchRepositoryException exception = Assertions.assertThrows(
                NoSuchRepositoryException.class,
                () -> repositoryService.putUserRepositoryByName("owner", "repo", any(UpdateRepositoryDto.class))
        );

        Assertions.assertEquals("No repository with name [repo] found for the user [owner].", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void deleteLocalUserRepositoryByName_RepositoryExists_RepositoryDeleted() {
        var repository = TestDataFactory.getTestRepository();
        when(repositoryRepository.findById(repository.getId())).thenReturn(Optional.of(repository));

        repositoryService.deleteLocalUserRepositoryByName(repository.getId().getOwnerName(), repository.getId().getRepositoryName());

        verify(repositoryRepository, times(1)).delete(repository);
    }

    @Test
    void deleteLocalUserRepositoryByName_RepositoryDoesNotExist_ExceptionThrown() {
        when(repositoryRepository.findById(any(RepositoryId.class))).thenReturn(Optional.empty());

        NoSuchRepositoryException exception = Assertions.assertThrows(
                NoSuchRepositoryException.class,
                () -> repositoryService.deleteLocalUserRepositoryByName("owner", "repo")
        );

        Assertions.assertEquals("No repository with name [repo] found for the user [owner].", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
