package com.orrganista.githubgrabber.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orrganista.githubgrabber.mapper.GithubRepositoryMapper;
import com.orrganista.githubgrabber.remote.github.GithubClient;
import com.orrganista.githubgrabber.remote.github.model.GithubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.FileUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    GithubClient githubClient;
    @Autowired
    GithubRepositoryMapper githubApiMapper;
    @Autowired
    ObjectMapper objectMapper;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");


    @Test
    void shouldReturnUserRepositories() throws Exception {
        var githubRepositoriesJson = FileUtil.readFromFileToString("/files/github/response_user_repositories.json");
        var githubRepositories = objectMapper.readValue(githubRepositoriesJson, new TypeReference<List<GithubRepository>>() {
        });

        when(githubClient.getUserRepositories("owner")).thenReturn(githubRepositories);
        var repositoryResponseDtoList = githubApiMapper.toRepositoryResponseDtoList(githubRepositories);

        mockMvc.perform(MockMvcRequestBuilders.get("/github-details/owner"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(result -> {
                    for (int i = 0; i < repositoryResponseDtoList.size(); i++) {
                        var dto = repositoryResponseDtoList.get(i);
                        String index = "$[" + i + "]";
                        jsonPath(index + ".fullName").value(dto.getFullName()).match(result);
                        jsonPath(index + ".description").value(dto.getDescription()).match(result);
                        jsonPath(index + ".cloneUrl").value(dto.getCloneUrl()).match(result);
                        jsonPath(index + ".stars").value(dto.getStars()).match(result);
                        jsonPath(index + ".createdAt").value(dto.getCreatedAt().format(dateTimeFormatter)).match(result);
                    }
                });
    }

    @Test
    void shouldReturnUserRepositoryByName() throws Exception {
        var githubRepositoryJson = FileUtil.readFromFileToString("/files/github/response_user_repository.json");
        var githubRepository = objectMapper.readValue(githubRepositoryJson, GithubRepository.class);

        when(githubClient.getUserRepositoryByName("owner", "repo")).thenReturn(Optional.of(githubRepository));
        var responseDtoRepository = githubApiMapper.toRepositoryResponseDto(githubRepository);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/repo"))
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value(responseDtoRepository.getFullName()))
                .andExpect(jsonPath("$.description").value(responseDtoRepository.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(responseDtoRepository.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(responseDtoRepository.getStars()))
                .andExpect(jsonPath("$.createdAt").value(responseDtoRepository.getCreatedAt().format(dateTimeFormatter)));
    }
}
