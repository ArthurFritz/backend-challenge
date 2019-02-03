package com.invillia.acme.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.server.ServerEndpoint;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResumeDTO {

    private LocalDateTime confirmationDate;
    private BigDecimal totalValue;

}
