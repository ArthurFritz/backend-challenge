package com.invillia.acme.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class ResumeDTO {

    private LocalDateTime confirmationDate;
    private BigDecimal totalValue;

}
