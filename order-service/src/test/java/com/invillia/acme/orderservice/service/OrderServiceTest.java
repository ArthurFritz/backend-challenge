package com.invillia.acme.orderservice.service;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.dto.ResumeDTO;
import com.invillia.acme.orderservice.dto.mapper.OrderMapper;
import com.invillia.acme.orderservice.entity.Order;
import com.invillia.acme.orderservice.entity.OrderItem;
import com.invillia.acme.orderservice.entity.QOrder;
import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.repository.OrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private static final String ORDER_ID = "123465";

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void should_getResumeNoElement(){
        thrown.expect(NoSuchElementException.class);
        orderService.getResume(ORDER_ID);
    }

    @Test
    public void should_getResume(){
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1L);
        orderItem.setUnitPrice(BigDecimal.TEN);
        order.setItems(Arrays.asList(orderItem));
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        ResumeDTO resume = orderService.getResume(ORDER_ID);
        Assert.assertEquals(BigDecimal.TEN, resume.getTotalValue());
        Assert.assertNull(resume.getConfirmationDate());
    }

    @Test
    public void should_updateOrderNotFound(){
        thrown.expect(NoSuchElementException.class);
        orderService.updateOrder(ORDER_ID, StatusOrder.COMPLETED);
    }

    @Test
    public void should_updateOrder(){
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(new Order()));
        orderService.updateOrder(ORDER_ID, StatusOrder.COMPLETED);
        Mockito.verify(orderRepository).save(any());
    }

}