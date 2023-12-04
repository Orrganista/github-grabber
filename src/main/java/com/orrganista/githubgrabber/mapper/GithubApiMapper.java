package com.orrganista.githubgrabber.mapper;

import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.remote.githubapi.model.Repository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GithubApiMapper {

    @Mapping(target = "stars", source = "repository.stargazersCount")
    RepositoryResponseDto toRepositoryResponseDto(Repository repository);

    List<RepositoryResponseDto> toRepositoryResponseDtoList(List<Repository> repositoryList);
}
