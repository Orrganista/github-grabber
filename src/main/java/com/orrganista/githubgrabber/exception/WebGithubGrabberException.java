package com.orrganista.githubgrabber.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class WebGithubGrabberException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WebGithubGrabberException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
