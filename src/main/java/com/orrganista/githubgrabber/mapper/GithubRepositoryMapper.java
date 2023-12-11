package com.orrganista.githubgrabber.mapper;

import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.remote.github.model.GithubRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GithubRepositoryMapper {

    @Mapping(target = "stars", source = "githubApiRepository.stargazersCount")
    RepositoryResponseDto toRepositoryResponseDto(GithubRepository githubApiRepository);

    List<RepositoryResponseDto> toRepositoryResponseDtoList(List<GithubRepository> githubApiRepositoryList);
}
