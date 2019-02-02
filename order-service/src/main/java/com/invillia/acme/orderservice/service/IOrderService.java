package com.invillia.acme.orderservice.service;

import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {

    OrderDTO createNewOrder(OrderDTO orderDTO);

    List<OrderDTO> listOrderService(String address, StatusOrder statusOrder, LocalDateTime localDateTime);
}
