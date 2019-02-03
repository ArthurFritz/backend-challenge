package com.invillia.acme.paymentservice.dto.mapper;

import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO entityToDto(Payment payment);

    Payment dtoToEntity(PaymentDTO paymentDTO);

}
