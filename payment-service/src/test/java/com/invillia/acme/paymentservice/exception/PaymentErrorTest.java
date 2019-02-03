package com.invillia.acme.paymentservice.exception;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentErrorTest {

    @Test
    public void PaymentError() {
        PaymentError paymentError = new PaymentError();
        Assert.assertEquals("There is already a payment", paymentError.getMessage());
    }
}