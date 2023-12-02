package com.orrganista.githubgrabber.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdAt;
}
