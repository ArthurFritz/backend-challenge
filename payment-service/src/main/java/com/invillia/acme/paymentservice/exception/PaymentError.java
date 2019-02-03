package com.invillia.acme.paymentservice.exception;

import com.invillia.acme.paymentservice.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentError extends RuntimeException {
    public PaymentError() {
        super("There is already a payment");
    }
}
