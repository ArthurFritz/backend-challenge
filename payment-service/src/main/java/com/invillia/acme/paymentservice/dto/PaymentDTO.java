package com.invillia.acme.paymentservice.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String id;

    @NotEmpty
    private String creditCardNumber;

    @NotEmpty
    private String order;


}
