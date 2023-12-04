package com.orrganista.githubgrabber.exception.handler;

import com.orrganista.githubgrabber.model.dto.MessageDto;
import com.orrganista.githubgrabber.exception.WebGithubGrabberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GithubGrabberExceptionHandler {

    @ExceptionHandler(WebGithubGrabberException.class)
    public ResponseEntity<MessageDto> handle(WebGithubGrabberException webGithubGrabberException) {
        return ResponseEntity.status(webGithubGrabberException.getHttpStatus()).body(
                MessageDto.builder()
                        .message(webGithubGrabberException.getMessage())
                        .status(webGithubGrabberException.getHttpStatus())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body("Github Grabber: something went wrong.");
    }
}
