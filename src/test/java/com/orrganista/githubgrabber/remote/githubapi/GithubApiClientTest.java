package com.orrganista.githubgrabber.remote.githubapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import util.TestDataFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWireMock(port = 8888)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GithubApiClientTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    GithubApiClient githubApiClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnUserRepositories() throws Exception {
        var repositoryList = TestDataFactory.getTestRepositoryList();
        wireMockServer.stubFor(get(urlEqualTo("/users/owner/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repositoryList))));

        var responseRepositoryList = githubApiClient.getUserRepositories("owner");

        verify(getRequestedFor(urlEqualTo("/users/owner/repos")));
        assertEquals(repositoryList.size(), responseRepositoryList.size());
        for (int i = 0; i < repositoryList.size(); i++) {
            var repository = repositoryList.get(i);
            var responseRepository = responseRepositoryList.get(i);
            assertEquals(repository.getFullName(), responseRepository.getFullName());
            assertEquals(repository.getDescription(), responseRepository.getDescription());
            assertEquals(repository.getCloneUrl(), responseRepository.getCloneUrl());
            assertEquals(repository.getStargazersCount(), responseRepository.getStargazersCount());
            assertEquals(repository.getCreatedAt(), responseRepository.getCreatedAt());
        }
    }

    @Test
    public void shouldReturnUserRepositoryByName() throws Exception {
        var repository = TestDataFactory.getTestRepository();
        wireMockServer.stubFor(get(urlEqualTo("/repos/owner/repo"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repository))));

        var responseRepository = githubApiClient.getUserRepositoryByName("owner", "repo").get();

        verify(getRequestedFor(urlEqualTo("/repos/owner/repo")));
        assertEquals(repository.getFullName(), responseRepository.getFullName());
        assertEquals(repository.getDescription(), responseRepository.getDescription());
        assertEquals(repository.getCloneUrl(), responseRepository.getCloneUrl());
        assertEquals(repository.getStargazersCount(), responseRepository.getStargazersCount());
        assertEquals(repository.getCreatedAt(), responseRepository.getCreatedAt());
    }
}
