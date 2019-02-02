package com.invillia.acme.orderservice.service;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.dto.mapper.OrderMapper;
import com.invillia.acme.orderservice.entity.QOrder;
import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.repository.OrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Before
    public void setUP() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createNewOrder() {
        orderService.createNewOrder(OrderDTO.builder().build());
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void should_findAllByParameters() {
        LocalDateTime localDateTime = LocalDateTime.now();
        BooleanExpression predicate = QOrder.order.address.containsIgnoreCase(StringUtils.defaultIfBlank("address", StringUtils.EMPTY));
        predicate.and(QOrder.order.statusOrder.eq(StatusOrder.NEW));
        predicate.and(QOrder.order.confirmationDate.eq(localDateTime));
        orderService.listOrderService("address", StatusOrder.NEW, localDateTime);
        Mockito.verify(orderRepository).findAll(predicate);
    }

    @Test
    public void should_findAllNoDateTime() {
        BooleanExpression predicate = QOrder.order.address.containsIgnoreCase(StringUtils.defaultIfBlank("address", StringUtils.EMPTY));
        predicate.and(QOrder.order.statusOrder.eq(StatusOrder.NEW));
        orderService.listOrderService("address", StatusOrder.NEW, null);
        Mockito.verify(orderRepository).findAll(predicate);
    }

    @Test
    public void should_findAllByParametersNoStatusOrder() {
        LocalDateTime localDateTime = LocalDateTime.now();
        BooleanExpression predicate = QOrder.order.address.containsIgnoreCase(StringUtils.defaultIfBlank("address", StringUtils.EMPTY));
        predicate.and(QOrder.order.confirmationDate.eq(localDateTime));
        orderService.listOrderService("address", null, localDateTime);
        Mockito.verify(orderRepository).findAll(predicate);
    }

}