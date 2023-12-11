package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.mapper.RepositoryMapper;
import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.repository.RepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class LocalControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RepositoryRepository repositoryRepository;
    @Autowired
    RepositoryMapper repositoryMapper;
    private List<Repository> repositories;


    @BeforeEach
    void setup() {
        repositories = repositoryRepository.findAll();
    }


    @Test
    void shouldReturnLocalUserRepositoryByName() throws Exception {
        var repository = repositories.get(0);
        var responseDto = repositoryMapper.toRepositoryResponseDto(repository);
        mockMvc.perform(MockMvcRequestBuilders.get("/local/repositories/{owner}/{repository-name}", repository.getId().getOwnerName(), repository.getId().getRepositoryName()))
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value(responseDto.getFullName()))
                .andExpect(jsonPath("$.description").value(responseDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(responseDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(responseDto.getStars()));
/*                .andExpect(jsonPath("$.createdAt").value(responseDto.getCreatedAt()));*/
    }
}
