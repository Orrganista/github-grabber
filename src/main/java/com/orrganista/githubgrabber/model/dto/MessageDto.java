package com.orrganista.githubgrabber.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDto {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
