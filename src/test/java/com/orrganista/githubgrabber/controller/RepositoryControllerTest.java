package com.orrganista.githubgrabber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orrganista.githubgrabber.mapper.RepositoryMapper;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.repository.RepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.TestDataFactory;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class RepositoryControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RepositoryRepository repositoryRepository;
    @Autowired
    ObjectMapper objectMapper;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    private List<Repository> repositories;


    @BeforeEach
    void setup() {
        repositories = repositoryRepository.findAll();
    }

    @Test
    void shouldCreateUserRepository() throws Exception {
        var createRepositoryDto = TestDataFactory.getTestCreateRepositoryDto();
        var createdAt = createRepositoryDto.getCreatedAt().withOffsetSameInstant(ZoneOffset.UTC).format(dateTimeFormatter);
        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/owner/repo")
                        .content(objectMapper.writeValueAsString(createRepositoryDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value("owner/repo"))
                .andExpect(jsonPath("$.description").value(createRepositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(createRepositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(createRepositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(createdAt));
    }

    @Test
    void shouldPutUserRepositoryByName() throws Exception {
        var existingRepository = repositories.get(0);
        var id = existingRepository.getId();
        var updateRepositoryDto = TestDataFactory.getTestUpdateRepositoryDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{owner}/{repository-name}", id.getOwnerName(), id.getRepositoryName())
                        .content(objectMapper.writeValueAsString(updateRepositoryDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value(id.toString()))
                .andExpect(jsonPath("$.description").value(updateRepositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(updateRepositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(updateRepositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(existingRepository.getCreatedAt().toString()));
    }

    @Test
    void shouldDeleteUserRepository() throws Exception {
        var id = repositories.get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/{owner}/{repository-name}", id.getOwnerName(), id.getRepositoryName()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
