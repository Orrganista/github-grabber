package com.orrganista.githubgrabber.remote.githubapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Repository {

    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final Integer stargazersCount;
    private final LocalDateTime createdAt;
}
