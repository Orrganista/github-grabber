package com.orrganista.githubgrabber.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UpdateRepositoryDto {
    private final String repositoryName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
}
