package com.invillia.acme.orderservice.dto.mapper;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.dto.OrderItemDTO;
import com.invillia.acme.orderservice.entity.Order;
import com.invillia.acme.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO entityToDto(Order order);

    Order dtoToEntity(OrderDTO orderDto);

    OrderItemDTO entityToDto(OrderItem order);

    OrderItem dtoToEntity(OrderItemDTO orderDto);

}
