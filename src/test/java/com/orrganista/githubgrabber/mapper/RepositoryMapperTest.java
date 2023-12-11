package com.orrganista.githubgrabber.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import util.TestDataFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepositoryMapperTest {

    private final RepositoryMapper repositoryMapper = Mappers.getMapper(RepositoryMapper.class);

    @Test
    public void toRepositoryResponseDto_DataCorrect_DtoReturned() {
        var repository = TestDataFactory.getTestRepository();

        var dto = repositoryMapper.toRepositoryResponseDto(repository);

        assertNotNull(dto);
        assertEquals(dto.getFullName(), repository.getId().toString());
        assertEquals(dto.getDescription(), repository.getDescription());
        assertEquals(dto.getCloneUrl(), repository.getCloneUrl());
        assertEquals(dto.getStars(), repository.getStars());
        assertEquals(dto.getCreatedAt(), repository.getCreatedAt());
    }

    @Test
    public void createRepositoryDtoToRepository_DataCorrect_RepositoryReturned() {
        var createRepositoryDto = TestDataFactory.getTestCreateRepositoryDto();

        var repository = repositoryMapper.toRepository(createRepositoryDto);

        assertNotNull(repository);
        assertEquals(repository.getDescription(), createRepositoryDto.getDescription());
        assertEquals(repository.getCloneUrl(), createRepositoryDto.getCloneUrl());
        assertEquals(repository.getStars(), createRepositoryDto.getStars());
        assertEquals(repository.getCreatedAt(), createRepositoryDto.getCreatedAt());
    }

    @Test
    public void updateRepositoryDtoToRepository_DataCorrect_RepositoryReturned() {
        var updateRepositoryDto = TestDataFactory.getTestUpdateRepositoryDto();

        var repository = repositoryMapper.toRepository(updateRepositoryDto);

        assertNotNull(repository);
        assertEquals(repository.getDescription(), updateRepositoryDto.getDescription());
        assertEquals(repository.getCloneUrl(), updateRepositoryDto.getCloneUrl());
        assertEquals(repository.getStars(), updateRepositoryDto.getStars());
    }
}
