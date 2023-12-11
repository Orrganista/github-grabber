package com.orrganista.githubgrabber.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class CreateRepositoryDto {
    private final String description;
    private final String cloneUrl;
    private final Integer stars;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private final OffsetDateTime createdAt;
}
