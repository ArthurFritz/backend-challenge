package com.invillia.acme.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidOrder extends RuntimeException {
    public InvalidOrder(String id) {
        super("Invalid order id " + id );
    }
}
