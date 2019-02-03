package com.invillia.acme.paymentservice.exception;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class InvalidValueRefundTest {

    @Test
    public void InvalidValueRefund() {
        InvalidValueRefund invalidValueRefund = new InvalidValueRefund(BigDecimal.TEN);
        Assert.assertEquals("This value not allow refund 10", invalidValueRefund.getMessage());
    }

}