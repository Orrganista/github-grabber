package com.orrganista.githubgrabber.remote.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class Repository {

    @JsonProperty("full_name")
    private final String fullName;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("clone_url")
    private final String cloneUrl;
    @JsonProperty("stargazers_count")
    private final Integer stargazersCount;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
}
