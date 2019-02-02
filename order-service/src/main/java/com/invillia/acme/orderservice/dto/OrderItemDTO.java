package com.invillia.acme.orderservice.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @NotEmpty
    private String description;

    @NotNull
    @Min(1)
    private Long quantity;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

}
