package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.DataNotFoundException;
import com.orrganista.githubgrabber.mapper.GithubRepositoryMapper;
import com.orrganista.githubgrabber.remote.github.GithubClient;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class GithubServiceTest {

    private GithubClient githubClient;
    private GithubService githubService;
    private GithubRepositoryMapper githubRepositoryMapper;


    @BeforeEach
    public void setup() {
        githubClient = Mockito.mock(GithubClient.class);
        githubRepositoryMapper = Mappers.getMapper(GithubRepositoryMapper.class);
        githubService = new GithubService(githubClient, githubRepositoryMapper);
    }

    @Test
    void getUserRepositories_RepositoriesExist_RepositoriesReturned() {
        // given
        var repositories = TestDataFactory.getTestGithubRepositories();
        var repositoryResponseDtoList = githubRepositoryMapper.toRepositoryResponseDtoList(repositories);
        when(githubClient.getUserRepositories("owner")).thenReturn(repositories);

        // when
        var result = githubService.getUserRepositories("owner");

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(repositoryResponseDtoList.get(0).getFullName(), result.get(0).getFullName());
        assertEquals(repositoryResponseDtoList.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(repositoryResponseDtoList.get(0).getCloneUrl(), result.get(0).getCloneUrl());
        assertEquals(repositoryResponseDtoList.get(0).getStars(), result.get(0).getStars());
        assertEquals(repositoryResponseDtoList.get(0).getCreatedAt(), result.get(0).getCreatedAt());
    }

    @Test
    void getUserRepositoryByName_RepositoryExist_RepositoryReturned() {
        var repository = TestDataFactory.getTestGithubRepository();
        var repositoryResponseDto = githubRepositoryMapper.toRepositoryResponseDto(repository);
        when(githubClient.getUserRepositoryByName("owner", repository.getFullName())).thenReturn(Optional.of(repository));

        var result = githubService.getUserRepositoryByName("owner", repository.getFullName());

        assertNotNull(result);
        assertEquals(repositoryResponseDto.getFullName(), result.getFullName());
        assertEquals(repositoryResponseDto.getDescription(), result.getDescription());
        assertEquals(repositoryResponseDto.getCloneUrl(), result.getCloneUrl());
        assertEquals(repositoryResponseDto.getStars(), result.getStars());
        assertEquals(repositoryResponseDto.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void getUserRepositoryByName_RepositoryDoesNotExist_ExceptionThrown() {
        when(githubClient.getUserRepositoryByName(anyString(), anyString())).thenReturn(Optional.empty());

        DataNotFoundException exception = Assertions.assertThrows(
                DataNotFoundException.class,
                () -> githubService.getUserRepositoryByName("owner", "repo")
        );

        Assertions.assertEquals("Repository not found with name: repo.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
