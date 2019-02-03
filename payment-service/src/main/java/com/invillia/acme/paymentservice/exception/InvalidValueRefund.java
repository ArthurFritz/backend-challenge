package com.invillia.acme.paymentservice.exception;

import com.invillia.acme.paymentservice.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueRefund extends RuntimeException {
    public InvalidValueRefund(BigDecimal value) {
        super("This value not allow refund " + value);
    }
}
