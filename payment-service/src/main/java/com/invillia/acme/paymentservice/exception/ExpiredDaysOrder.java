package com.invillia.acme.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpiredDaysOrder extends RuntimeException {
    public ExpiredDaysOrder(LocalDateTime localDateTime) {
        super("This confirmation in the " + localDateTime + " not allow refund");
    }
}
