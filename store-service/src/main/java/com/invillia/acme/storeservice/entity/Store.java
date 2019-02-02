package com.invillia.acme.storeservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Store {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String address;

}
