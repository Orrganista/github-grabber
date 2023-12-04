package com.orrganista.githubgrabber.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import util.TestDataFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GithubApiMapperTest {

    private final GithubApiMapper githubApiMapper = Mappers.getMapper(GithubApiMapper.class);

    @Test
    public void toRepositoryResponseDto_DataCorrect_DtoReturned() {
        var repository = TestDataFactory.getTestRepository();

        var dto = githubApiMapper.toRepositoryResponseDto(repository);

        assertNotNull(dto);
        assertEquals(dto.getFullName(), repository.getFullName());
        assertEquals(dto.getDescription(), repository.getDescription());
        assertEquals(dto.getCloneUrl(), repository.getCloneUrl());
        assertEquals(dto.getStars(), repository.getStargazersCount());
        assertEquals(dto.getCreatedAt(), repository.getCreatedAt());
    }

    @Test
    public void toRepositoryResponseDtoList_DataCorrect_DtoListReturned() {
        var repositoryList = TestDataFactory.getTestRepositoryList();

        var dtoList = githubApiMapper.toRepositoryResponseDtoList(repositoryList);

        assertNotNull(dtoList);
        assertEquals(dtoList.size(), repositoryList.size());
        assertEquals(dtoList.get(0).getFullName(), repositoryList.get(0).getFullName());
        assertEquals(dtoList.get(0).getDescription(), repositoryList.get(0).getDescription());
        assertEquals(dtoList.get(0).getCloneUrl(), repositoryList.get(0).getCloneUrl());
        assertEquals(dtoList.get(0).getStars(), repositoryList.get(0).getStargazersCount());
        assertEquals(dtoList.get(0).getCreatedAt(), repositoryList.get(0).getCreatedAt());
    }
}
