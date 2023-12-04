package com.orrganista.githubgrabber.remote.githubapi;

import com.orrganista.githubgrabber.remote.FeignConfig;
import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "githubApi", configuration = FeignConfig.class)
public interface GithubApiClient {

    @GetMapping("/users/{owner}/repos")
    @Headers("Accept: application/vnd.github+json")
    List<Repository> getUserRepositories(@PathVariable("owner") String owner);

    @GetMapping("/repos/{owner}/{repo}")
    @Headers("Accept: application/vnd.github+json")
    Optional<Repository> getUserRepositoryByName(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
