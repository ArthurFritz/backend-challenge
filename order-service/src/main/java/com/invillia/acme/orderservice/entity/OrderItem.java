package com.invillia.acme.orderservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {

    @NonNull
    private String description;

    @NonNull
    private Long quantity;

    @NonNull
    private BigDecimal unitPrice;

    public BigDecimal totalValue(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
