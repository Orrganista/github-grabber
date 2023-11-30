package com.orrganista.githubgrabber.remote;

import com.orrganista.githubgrabber.exception.DataForbiddenException;
import com.orrganista.githubgrabber.exception.DataMovedPermanentlyException;
import com.orrganista.githubgrabber.exception.DataNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 301:
                return new DataMovedPermanentlyException("Moved permanently.");
            case 403:
                return new DataForbiddenException("Data forbidden.");
            case 404:
                return new DataNotFoundException("Data not found.");
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
