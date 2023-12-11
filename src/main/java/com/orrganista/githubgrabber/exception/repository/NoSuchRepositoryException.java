package com.orrganista.githubgrabber.exception.repository;

import com.orrganista.githubgrabber.exception.WebGithubGrabberException;
import org.springframework.http.HttpStatus;

public class NoSuchRepositoryException extends WebGithubGrabberException {
    public NoSuchRepositoryException(String owner, String repo) {
        super(String.format("No repository with name [%s] found for the user [%s].", repo, owner), HttpStatus.NOT_FOUND);
    }
}
