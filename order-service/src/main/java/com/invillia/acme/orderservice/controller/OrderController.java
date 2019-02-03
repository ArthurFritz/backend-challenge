package com.invillia.acme.orderservice.controller;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> newOrder(@RequestBody @Valid OrderDTO dto) {
        dto.setStatusOrder(StatusOrder.NEW);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createNewOrder(dto));
    }

    @GetMapping
    public List<OrderDTO> findByParameters(
            @RequestParam(required = false) StatusOrder status,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime confirmationDate) {
        return orderService.listOrderService(address, status, confirmationDate);
    }

}
