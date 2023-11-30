package com.orrganista.githubgrabber.exception.handler;

import com.orrganista.githubgrabber.model.dto.MessageDto;
import com.orrganista.githubgrabber.exception.WebGithubGrabberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception exception) {
        return ResponseEntity.badRequest().body("Github Grabber: something went wrong.");
    }
}
