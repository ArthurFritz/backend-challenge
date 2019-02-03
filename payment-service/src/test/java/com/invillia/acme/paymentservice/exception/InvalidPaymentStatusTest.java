package com.invillia.acme.paymentservice.exception;

import com.invillia.acme.paymentservice.enums.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;

public class InvalidPaymentStatusTest {

    @Test
    public void InvalidPaymentStatus() {
        InvalidPaymentStatus invalidPaymentStatus = new InvalidPaymentStatus(PaymentStatus.REFUND);
        Assert.assertEquals("Invalid status payment to refund REFUND", invalidPaymentStatus.getMessage());
    }
}