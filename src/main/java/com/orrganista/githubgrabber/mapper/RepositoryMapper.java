package com.orrganista.githubgrabber.mapper;

import com.orrganista.githubgrabber.model.dto.CreateRepositoryDto;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.model.dto.UpdateRepositoryDto;
import com.orrganista.githubgrabber.model.entity.Repository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RepositoryMapper {

    @Mapping(target = "fullName", expression = "java(repository.getId().toString())")
    RepositoryResponseDto toRepositoryResponseDto(Repository repository);

    Repository toRepository(CreateRepositoryDto createRepositoryDto);

    Repository toRepository(UpdateRepositoryDto updateRepositoryDto);
}
