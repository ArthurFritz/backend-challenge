package com.invillia.acme.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundPayment extends RuntimeException {
    public NotFoundPayment(String id) {
        super("Not found payment id " + id );
    }
}
