package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.RefundDTO;
import com.invillia.acme.paymentservice.dto.mapper.PaymentMapper;
import com.invillia.acme.paymentservice.entity.Payment;
import com.invillia.acme.paymentservice.enums.PaymentStatus;
import com.invillia.acme.paymentservice.exception.*;
import com.invillia.acme.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class RefundService implements IRefundService {

    private static final int LIMIT_DAYS_REFUND = 10;
    private PaymentRepository paymentRepository;

    @Value("${server.order-service}")
    private String serverOrder;
    private final RestTemplate restTemplate;

    public RefundService(PaymentRepository paymentRepository, RestTemplate restTemplate){
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public RefundDTO refundOrder(RefundDTO dto) {
        Payment paymentInfo = paymentRepository.findById(dto.getPayment()).orElseThrow(()-> new NotFoundPayment(dto.getPayment()));
        validateOrder(paymentInfo, dto);
        paymentInfo.setPaymentStatus(PaymentStatus.REFUND);
        paymentInfo.setValueRefund(dto.getValueRefund());
        restTemplate.put(String.format("http://%s/api/v1/internal/update/%s/REFUND", serverOrder, dto.getOrder()), String.class);
        paymentRepository.save(paymentInfo);
        return dto;
    }

    private void validateOrder(Payment payment, RefundDTO dto){
        if(!payment.getOrder().equals(dto.getOrder())){
            throw new InvalidOrder(dto.getOrder());
        }
        if(!PaymentStatus.COMPLETED.equals(payment.getPaymentStatus())){
            throw new InvalidPaymentStatus(payment.getPaymentStatus());
        }
        if(payment.getTotalOrder().compareTo(dto.getValueRefund()) < 0){
            throw new InvalidValueRefund(dto.getValueRefund());
        }
        if(ChronoUnit.DAYS.between(payment.getOrderConfirmationDate(), LocalDateTime.now()) > LIMIT_DAYS_REFUND){
            throw new ExpiredDaysOrder(payment.getOrderConfirmationDate());
        }
    }
}
