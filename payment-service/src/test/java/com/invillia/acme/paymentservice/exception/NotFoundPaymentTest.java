package com.invillia.acme.paymentservice.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundPaymentTest {

    @Test
    public void NotFoundPayment() {
        NotFoundPayment notFoundPayment = new NotFoundPayment("tes1");
        Assert.assertEquals("Not found payment id tes1", notFoundPayment.getMessage());
    }

}