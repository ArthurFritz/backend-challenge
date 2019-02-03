package com.invillia.acme.paymentservice.exception;

import com.invillia.acme.paymentservice.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPaymentStatus extends RuntimeException {
    public InvalidPaymentStatus(PaymentStatus paymentStatus) {
        super("Invalid status payment to refund " + paymentStatus);
    }
}
