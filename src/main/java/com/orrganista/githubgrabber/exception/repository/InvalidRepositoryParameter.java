package com.orrganista.githubgrabber.exception.repository;

import com.orrganista.githubgrabber.exception.WebGithubGrabberException;
import org.springframework.http.HttpStatus;

public class InvalidRepositoryParameter extends WebGithubGrabberException {

    public InvalidRepositoryParameter(String parameter, String value) {
        super(String.format("Parameter [%s] is invalid for value [%s].", parameter, value), HttpStatus.BAD_REQUEST);
    }
}
