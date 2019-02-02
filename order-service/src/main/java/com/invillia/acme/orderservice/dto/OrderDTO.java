package com.invillia.acme.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.invillia.acme.orderservice.enums.StatusOrder;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String id;

    @NotNull
    private LocalDateTime confirmationDate;

    @NotEmpty
    private String address;

    @NotNull
    private StatusOrder statusOrder;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderItemDTO> items;
}
