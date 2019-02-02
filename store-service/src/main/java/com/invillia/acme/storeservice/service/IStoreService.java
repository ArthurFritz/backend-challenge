package com.invillia.acme.storeservice.service;

import com.invillia.acme.storeservice.dto.StoreDTO;

import java.util.List;

public interface IStoreService {

    StoreDTO addStore(StoreDTO storeDTO);

    StoreDTO updateStore(String id, StoreDTO storeDTO);

    List<StoreDTO> findAllByParameters(String address, String name);
    
}
