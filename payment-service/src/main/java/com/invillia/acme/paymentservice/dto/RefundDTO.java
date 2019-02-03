package com.invillia.acme.paymentservice.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.websocket.server.ServerEndpoint;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {

    @NotBlank
    private String payment;

    @NotBlank
    private String order;

    @DecimalMin("0.01")
    private BigDecimal valueRefund;

}
