package com.invillia.acme.paymentservice.service;

import com.invillia.acme.paymentservice.dto.RefundDTO;
import com.invillia.acme.paymentservice.entity.Payment;
import com.invillia.acme.paymentservice.enums.PaymentStatus;
import com.invillia.acme.paymentservice.exception.*;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class RefundServiceTest {

    private static final String ID_ORDER = "idOrder";
    private static final String PAYMENT = "123";

    @InjectMocks
    private RefundService refundService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUP() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_refundOrderNotFound() {
        thrown.expect(NotFoundPayment.class);
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).build());
    }

    @Test
    public void should_refundOrderInvalidOrder() {
        thrown.expect(InvalidOrder.class);
        Payment payment = new Payment();
        payment.setOrder("idOrderOther");
        payment.setTotalOrder(BigDecimal.ONE);
        payment.setOrderConfirmationDate(LocalDateTime.MIN);
        Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).valueRefund(BigDecimal.TEN).build());
    }

    @Test
    public void should_refundOrderInvalidStatusPayment() {
        thrown.expect(InvalidPaymentStatus.class);
        Payment payment = new Payment();
        payment.setOrder(ID_ORDER);
        payment.setTotalOrder(BigDecimal.ONE);
        payment.setOrderConfirmationDate(LocalDateTime.MIN);
        Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).valueRefund(BigDecimal.TEN).build());
    }

    @Test
    public void should_refundOrderInvalidAmountRefund() {
        thrown.expect(InvalidValueRefund.class);
        Payment payment = new Payment();
        payment.setOrder(ID_ORDER);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setTotalOrder(BigDecimal.ONE);
        payment.setOrderConfirmationDate(LocalDateTime.MIN);
        Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).valueRefund(BigDecimal.TEN).build());
    }

    @Test
    public void should_refundOrderInvalidDays() {
        thrown.expect(ExpiredDaysOrder.class);
        Payment payment = new Payment();
        payment.setOrder(ID_ORDER);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setTotalOrder(BigDecimal.TEN);
        payment.setOrderConfirmationDate(LocalDateTime.MIN);
        Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).valueRefund(BigDecimal.TEN).build());
    }

    @Test
    public void should_refundOrder() {
        Payment payment = new Payment();
        payment.setOrder(ID_ORDER);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setTotalOrder(BigDecimal.TEN);
        payment.setOrderConfirmationDate(LocalDateTime.now());
        Mockito.when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        refundService.refundOrder(RefundDTO.builder().order(ID_ORDER).payment(PAYMENT).valueRefund(BigDecimal.TEN).build());
    }
}