package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import com.orrganista.githubgrabber.service.GithubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github-details")
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiService githubApiService;

    @GetMapping("/{owner}")
    public List<Repository> getUserRepositories(@PathVariable String owner) {
        return githubApiService.GetUserRepositories(owner);
    }

    @GetMapping("/{owner}/{repo}")
    public Repository getUserRepositoryByName(@PathVariable String owner, @PathVariable String repo) {
        return githubApiService.GetRepositoryByName(owner, repo);
    }
}
