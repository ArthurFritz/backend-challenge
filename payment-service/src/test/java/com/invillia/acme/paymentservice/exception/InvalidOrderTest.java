package com.invillia.acme.paymentservice.exception;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class InvalidOrderTest {

    @Test
    public void InvalidOrder() {
        InvalidOrder invalidOrder = new InvalidOrder("123");
        Assert.assertEquals("Invalid order id 123", invalidOrder.getMessage());
    }
}