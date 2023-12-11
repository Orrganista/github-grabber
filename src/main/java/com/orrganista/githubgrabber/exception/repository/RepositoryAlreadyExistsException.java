package com.orrganista.githubgrabber.exception.repository;

import com.orrganista.githubgrabber.exception.WebGithubGrabberException;
import org.springframework.http.HttpStatus;

public class RepositoryAlreadyExistsException extends WebGithubGrabberException  {
    public RepositoryAlreadyExistsException(String owner, String repo) {
        super(String.format("Repository [%s] for user [%s] already exists.", repo, owner), HttpStatus.BAD_REQUEST);
    }
}
