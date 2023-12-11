package com.orrganista.githubgrabber.remote.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.orrganista.githubgrabber.remote.github.model.GithubRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import util.FileUtil;
import util.TestDataFactory;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWireMock(port = 8888)
public final class GithubClientTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    GithubClient githubClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnUserRepositories() throws Exception {
        var repositoriesJson = FileUtil.readFromFileToString("/files/github/response_user_repositories.json");
        var repositories = objectMapper.readValue(repositoriesJson, new TypeReference<List<GithubRepository>>() {
        });
        wireMockServer.stubFor(get(urlEqualTo("/users/owner/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(repositoriesJson)));

        var responseRepositories = githubClient.getUserRepositories("owner");

        verify(getRequestedFor(urlEqualTo("/users/owner/repos")));
        assertEquals(repositories.size(), responseRepositories.size());
        for (int i = 0; i < repositories.size(); i++) {
            var repository = repositories.get(i);
            var responseRepository = responseRepositories.get(i);
            assertEquals(repository.getFullName(), responseRepository.getFullName());
            assertEquals(repository.getDescription(), responseRepository.getDescription());
            assertEquals(repository.getCloneUrl(), responseRepository.getCloneUrl());
            assertEquals(repository.getStargazersCount(), responseRepository.getStargazersCount());
            assertEquals(repository.getCreatedAt(), responseRepository.getCreatedAt());
        }
    }

    @Test
    public void shouldReturnUserRepositoryByName() throws Exception {
        var repositoryJson = FileUtil.readFromFileToString("/files/github/response_user_repository.json");
        var repository = objectMapper.readValue(repositoryJson, GithubRepository.class);
        wireMockServer.stubFor(get(urlEqualTo("/repos/owner/repo"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(repositoryJson)));

        var responseRepository = githubClient.getUserRepositoryByName("owner", "repo").get();

        verify(getRequestedFor(urlEqualTo("/repos/owner/repo")));
        assertEquals(repository.getFullName(), responseRepository.getFullName());
        assertEquals(repository.getDescription(), responseRepository.getDescription());
        assertEquals(repository.getCloneUrl(), responseRepository.getCloneUrl());
        assertEquals(repository.getStargazersCount(), responseRepository.getStargazersCount());
        assertEquals(repository.getCreatedAt(), responseRepository.getCreatedAt());
    }
}
