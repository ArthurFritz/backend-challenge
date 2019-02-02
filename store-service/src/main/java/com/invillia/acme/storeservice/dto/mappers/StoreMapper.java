package com.invillia.acme.storeservice.dto.mappers;

import com.invillia.acme.storeservice.dto.StoreDTO;
import com.invillia.acme.storeservice.entity.Store;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    Store dtoToEntity(StoreDTO dto);

    StoreDTO entityToDto(Store store);
}
