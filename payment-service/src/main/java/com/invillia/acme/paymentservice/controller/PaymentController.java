package com.invillia.acme.paymentservice.controller;

import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.dto.RefundDTO;
import com.invillia.acme.paymentservice.service.PaymentService;
import com.invillia.acme.paymentservice.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final RefundService refundService;

    public PaymentController(PaymentService paymentService, RefundService refundService) {
        this.paymentService = paymentService;
        this.refundService = refundService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> payOrder(@RequestBody @Valid PaymentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.paymentOrder(dto));
    }

    @PutMapping("/refund")
    public RefundDTO refundOrder(@RequestBody @Valid RefundDTO dto) {
        return refundService.refundOrder(dto);
    }
}
