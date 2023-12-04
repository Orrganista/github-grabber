package com.orrganista.githubgrabber.exception;

import org.springframework.http.HttpStatus;

public class DataForbiddenException extends WebGithubGrabberException {
    public DataForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
