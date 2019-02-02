package com.invillia.acme.storeservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StoreDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

}
