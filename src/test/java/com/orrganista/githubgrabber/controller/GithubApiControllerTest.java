package com.orrganista.githubgrabber.controller;


import com.jayway.jsonpath.JsonPath;
import com.orrganista.githubgrabber.mapper.GithubApiMapper;
import com.orrganista.githubgrabber.remote.githubapi.GithubApiClient;
import com.orrganista.githubgrabber.service.GithubApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.GithubApiUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class GithubApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GithubApiClient githubApiClient;

    @Autowired
    GithubApiMapper githubApiMapper;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    void shouldReturnUserRepositories() throws Exception {
        var repositories = GithubApiUtil.getTestRepositoryList();
        when(githubApiClient.getUserRepositories("owner")).thenReturn(repositories);
        var dtoRepositories = githubApiMapper.toRepositoryResponseDtoList(repositories);

        mockMvc.perform(MockMvcRequestBuilders.get("/github-details/owner"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(result -> {
                    for (int i = 0; i < dtoRepositories.size(); i++) {
                        var dto = dtoRepositories.get(i);
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
        var repository = GithubApiUtil.getTestRepository();
        when(githubApiClient.getUserRepositoryByName("owner", "repo")).thenReturn(Optional.of(repository));
        var dtoRepository = githubApiMapper.toRepositoryResponseDto(repository);

        mockMvc.perform(MockMvcRequestBuilders.get("/github-details/owner/repo"))
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value(dtoRepository.getFullName()))
                .andExpect(jsonPath("$.description").value(dtoRepository.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(dtoRepository.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(dtoRepository.getStars()))
                .andExpect(jsonPath("$.createdAt").value(dtoRepository.getCreatedAt().format(dateTimeFormatter)));
    }
}
