package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.DataNotFoundException;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.mapper.GithubApiMapper;
import com.orrganista.githubgrabber.remote.githubapi.GithubApiClient;
import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubApiService {

    private final GithubApiClient githubApiClient;
    private final GithubApiMapper githubApiMapper;


    public List<RepositoryResponseDto> getUserRepositories(String owner) {
        List<Repository> repositoryList = githubApiClient.getUserRepositories(owner);
        return githubApiMapper.toRepositoryResponseDtoList(repositoryList);
    }

    public RepositoryResponseDto getUserRepositoryByName(String owner, String repo) {
        Repository repository = githubApiClient.getUserRepositoryByName(owner, repo)
                .orElseThrow(() -> new DataNotFoundException(String.format("Repository not found with name: %s.", repo)));
        return githubApiMapper.toRepositoryResponseDto(repository);
    }
}
