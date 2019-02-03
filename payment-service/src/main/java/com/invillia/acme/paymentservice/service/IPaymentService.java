package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.PaymentDTO;

public interface IPaymentService {

    PaymentDTO paymentOrder(PaymentDTO dto);

}
