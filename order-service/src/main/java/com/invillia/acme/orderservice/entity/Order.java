package com.invillia.acme.orderservice.entity;

import com.invillia.acme.orderservice.enums.StatusOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Order {

    @Id
    private String id;

    @NonNull
    private LocalDateTime confirmationDate;

    @NonNull
    private String address;

    @NonNull
    private StatusOrder statusOrder;

    private List<OrderItem> items;
}
