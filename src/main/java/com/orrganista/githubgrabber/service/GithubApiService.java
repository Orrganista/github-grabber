package com.orrganista.githubgrabber.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orrganista.githubgrabber.GithubGrabberApplication;
import com.orrganista.githubgrabber.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.remote.githubapi.GithubApiClient;
import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubApiService {

    private final GithubApiClient githubApiClient;
    private final ObjectMapper objectMapper;

    public List<Repository> GetUserRepositories(String owner) {
        return githubApiClient.getUserRepositories(owner);
    }

    public Repository GetRepositoryByName(String owner, String repo) {
        return githubApiClient.getUserRepositoryByName(owner, repo);
    }
}
