package com.orrganista.githubgrabber.exception;

import org.springframework.http.HttpStatus;

public class DataMovedPermanentlyException extends WebGithubGrabberException {
    public DataMovedPermanentlyException(String message) {
        super(message, HttpStatus.MOVED_PERMANENTLY);
    }
}
