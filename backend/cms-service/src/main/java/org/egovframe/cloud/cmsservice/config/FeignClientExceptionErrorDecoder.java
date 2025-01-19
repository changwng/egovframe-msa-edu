package org.egovframe.cloud.cmsservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
            case 404:
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
            case 429:
                return new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
            default:
                return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
