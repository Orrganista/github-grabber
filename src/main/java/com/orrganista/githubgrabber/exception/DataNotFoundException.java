package com.orrganista.githubgrabber.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends WebGithubGrabberException {
    public DataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
