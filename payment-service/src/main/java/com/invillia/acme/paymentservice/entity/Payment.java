package com.invillia.acme.paymentservice.entity;

import com.invillia.acme.paymentservice.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Payment {

    @Id
    private String id;

    @NonNull
    private String creditCardNumber;

    @NonNull
    private String order;

    @NonNull
    private PaymentStatus paymentStatus;

    private LocalDateTime orderConfirmationDate;

    private BigDecimal totalOrder;

    private BigDecimal valueRefund;

}
