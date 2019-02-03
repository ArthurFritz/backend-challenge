package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.dto.RefundDTO;

@FunctionalInterface
public interface IRefundService {

    RefundDTO refundOrder(RefundDTO dto);

}
