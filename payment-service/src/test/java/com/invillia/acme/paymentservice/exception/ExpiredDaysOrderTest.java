package com.invillia.acme.paymentservice.exception;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class ExpiredDaysOrderTest {

    @Test
    public void ExpiredDays() {
        LocalDateTime now = LocalDateTime.now();
        ExpiredDaysOrder expiredDaysOrder = new ExpiredDaysOrder(now);
        Assert.assertEquals("This confirmation in the " + expiredDaysOrder + " not allow refund", expiredDaysOrder.getMessage());
    }

}