package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.DataNotFoundException;
import com.orrganista.githubgrabber.mapper.GithubApiMapper;
import com.orrganista.githubgrabber.remote.githubapi.GithubApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import util.GithubApiUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubApiServiceTest {

    private GithubApiClient githubApiClient;
    private GithubApiService githubApiService;
    private GithubApiMapper githubApiMapper;

    @BeforeEach
    public void setup() {
        githubApiClient = Mockito.mock(GithubApiClient.class);
        githubApiMapper = Mappers.getMapper(GithubApiMapper.class);
        githubApiService = new GithubApiService(githubApiClient, githubApiMapper);
    }

    @Test
    void getUserRepositories_RepositoriesExist_RepositoriesReturned() {
        // given
        var repositoryList = GithubApiUtil.getTestRepositoryList();
        var repositoryResponseDtoList = githubApiMapper.toRepositoryResponseDtoList(repositoryList);
        when(githubApiClient.getUserRepositories("owner")).thenReturn(repositoryList);

        // when
        var result = githubApiService.getUserRepositories("owner");

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
        var repository = GithubApiUtil.getTestRepository();
        var repositoryResponseDto = githubApiMapper.toRepositoryResponseDto(repository);
        when(githubApiClient.getUserRepositoryByName("owner", repository.getFullName())).thenReturn(Optional.of(repository));

        var result = githubApiService.getUserRepositoryByName("owner", repository.getFullName());

        assertNotNull(result);
        assertEquals(repositoryResponseDto.getFullName(), result.getFullName());
        assertEquals(repositoryResponseDto.getDescription(), result.getDescription());
        assertEquals(repositoryResponseDto.getCloneUrl(), result.getCloneUrl());
        assertEquals(repositoryResponseDto.getStars(), result.getStars());
        assertEquals(repositoryResponseDto.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void getUserRepositoryByName_RepositoryDoesNotExist_ExceptionThrown() {
        when(githubApiClient.getUserRepositoryByName(anyString(), anyString())).thenReturn(Optional.empty());

        DataNotFoundException exception = Assertions.assertThrows(
                DataNotFoundException.class,
                () -> githubApiService.getUserRepositoryByName("owner", "repoName")
        );

        Assertions.assertEquals("Repository not found with name: repoName.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
