package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.dto.ResumeDTO;
import com.invillia.acme.paymentservice.dto.mapper.PaymentMapper;
import com.invillia.acme.paymentservice.entity.Payment;
import com.invillia.acme.paymentservice.enums.PaymentStatus;
import com.invillia.acme.paymentservice.exception.PaymentError;
import com.invillia.acme.paymentservice.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PaymentMapper paymentMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUP() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_paymentOrderThisDuplicatePaymentException() {
        thrown.expect(PaymentError.class);
        Mockito.when(paymentMapper.dtoToEntity(any())).thenReturn(new Payment());
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PROCESSING);
        Mockito.when(paymentRepository.findAllByOrder("orderId")).thenReturn(Arrays.asList(payment));

        paymentService.paymentOrder(PaymentDTO.builder().order("orderId").creditCardNumber("creditCard").build());
        Mockito.verify(paymentRepository, Mockito.times(0)).save(any());
    }

    @Test
    public void should_paymentOrder() {
        Mockito.when(paymentMapper.dtoToEntity(any())).thenReturn(new Payment());
        Mockito.when(restTemplate.getForObject("http://null/api/v1/internal/resume/orderId", ResumeDTO.class)).thenReturn(new ResumeDTO());

        paymentService.paymentOrder(PaymentDTO.builder().order("orderId").creditCardNumber("creditCard").build());
        Mockito.verify(paymentRepository, Mockito.times(1)).save(any());
    }
}