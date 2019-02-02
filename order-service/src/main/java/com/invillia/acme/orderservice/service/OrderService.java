package com.invillia.acme.orderservice.service;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.dto.mapper.OrderMapper;
import com.invillia.acme.orderservice.entity.QOrder;
import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.repository.OrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
}
