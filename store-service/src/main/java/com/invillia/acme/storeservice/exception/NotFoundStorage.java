package com.invillia.acme.storeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundStorage extends RuntimeException {
    public NotFoundStorage(String id) {
        super("Not found storage id " + id );
    }
}
