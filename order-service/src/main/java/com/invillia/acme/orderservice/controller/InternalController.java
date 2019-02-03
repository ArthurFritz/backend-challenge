package com.invillia.acme.orderservice.controller;

import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.service.OrderService;
import com.invillia.acme.orderservice.dto.ResumeDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/internal")
public class InternalController {

    private final OrderService orderService;

    public InternalController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Async
    @PutMapping("/update/{orderId}/{statusOrder}")
    public void updateStatus(@PathVariable String orderId, @PathVariable StatusOrder statusOrder) throws InterruptedException {
        orderService.updateOrder(orderId, statusOrder);
    }

    @GetMapping("/resume/{orderId}")
    public ResumeDTO getResume(@PathVariable String orderId) {
        return orderService.getResume(orderId);
    }

}
