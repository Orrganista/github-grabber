package com.orrganista.githubgrabber.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RepositoryResponseDto {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    private final LocalDateTime createdAt;
}
