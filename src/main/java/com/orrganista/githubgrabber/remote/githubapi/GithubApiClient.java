package com.orrganista.githubgrabber.remote.githubapi;

import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "githubApi")
public interface GithubApiClient {

    @GetMapping("/users/{owner}/repos")
    List<Repository> getUserRepositories(@PathVariable("owner") String owner);

    @GetMapping("/repos/{owner}/{repo}")
    Repository getUserRepositoryByName(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
