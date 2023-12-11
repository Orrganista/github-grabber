package com.orrganista.githubgrabber.service;

import com.orrganista.githubgrabber.exception.DataNotFoundException;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.mapper.GithubRepositoryMapper;
import com.orrganista.githubgrabber.remote.github.GithubClient;
import com.orrganista.githubgrabber.remote.github.model.GithubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubService {

    private final GithubClient githubApiClient;
    private final GithubRepositoryMapper githubApiMapper;


    public List<RepositoryResponseDto> getUserRepositories(String owner) {
        List<GithubRepository> repositoryList = githubApiClient.getUserRepositories(owner);
        return githubApiMapper.toRepositoryResponseDtoList(repositoryList);
    }

    public RepositoryResponseDto getUserRepositoryByName(String owner, String repo) {
        GithubRepository repository = githubApiClient.getUserRepositoryByName(owner, repo)
                .orElseThrow(() -> new DataNotFoundException(String.format("Repository not found with name: %s.", repo)));
        log.info("Retrieved repository {}.", repository.getFullName());
        return githubApiMapper.toRepositoryResponseDto(repository);
    }
}
