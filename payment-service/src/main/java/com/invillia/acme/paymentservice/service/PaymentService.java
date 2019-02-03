package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.dto.ResumeDTO;
import com.invillia.acme.paymentservice.dto.mapper.PaymentMapper;
import com.invillia.acme.paymentservice.entity.Payment;
import com.invillia.acme.paymentservice.enums.PaymentStatus;
import com.invillia.acme.paymentservice.exception.InvalidOrder;
import com.invillia.acme.paymentservice.exception.PaymentError;
import com.invillia.acme.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {

    @Value("${server.order-service}")
    private String serverOrder;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public PaymentDTO paymentOrder(PaymentDTO dto) {
        if (paymentRepository.findAllByOrder(dto.getOrder()).stream()
                .filter(i -> !PaymentStatus.ERROR.equals(i.getPaymentStatus()))
                .collect(Collectors.toList()).size() > 0) {
            throw new PaymentError();
        }
        Payment payment = paymentMapper.dtoToEntity(dto);
        ResumeDTO resume;
        try {
            resume = restTemplate.getForObject(String.format("http://%s/api/v1/internal/resume/%s", serverOrder, dto.getOrder()), ResumeDTO.class);
        } catch (HttpClientErrorException ex) {
            throw new InvalidOrder(dto.getOrder());
        }
        payment.setOrderConfirmationDate(resume.getConfirmationDate());
        payment.setTotalOrder(resume.getTotalValue());
        restTemplate.put(String.format("http://%s/api/v1/internal/update/%s/COMPLETED", serverOrder, dto.getOrder()), String.class);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        return paymentMapper.entityToDto(paymentRepository.save(payment));
    }

}
