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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        return orderMapper.entityToDto(orderRepository.save(orderMapper.dtoToEntity(orderDTO)));
    }

    @Override
    public List<OrderDTO> listOrderService(String address, StatusOrder statusOrder, LocalDateTime localDateTime) {
        BooleanExpression predicate = QOrder.order.address.containsIgnoreCase(StringUtils.defaultIfBlank(address, StringUtils.EMPTY));
        if (statusOrder != null) {
            predicate.and(QOrder.order.statusOrder.eq(statusOrder));
        }
        if (localDateTime != null) {
            predicate.and(QOrder.order.confirmationDate.eq(localDateTime));
        }
        return StreamSupport.stream(orderRepository.findAll(predicate).spliterator(), false).map(orderMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public ResumeDTO getResume(String orderId) {
        return orderRepository.findById(orderId)
                .map(order -> ResumeDTO.builder()
                        .confirmationDate(order.getConfirmationDate())
                        .totalValue(order.getItems().stream().map(OrderItem::totalValue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                        .build()).get();
    }

    public void updateOrder(String orderId, StatusOrder statusOrder) {
        Order order = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        order.setStatusOrder(statusOrder);
        orderRepository.save(order);
    }
}
