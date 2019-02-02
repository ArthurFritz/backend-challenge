package com.invillia.acme.storeservice.service;

import com.invillia.acme.storeservice.dto.StoreDTO;
import com.invillia.acme.storeservice.dto.mappers.StoreMapper;
import com.invillia.acme.storeservice.entity.QStore;
import com.invillia.acme.storeservice.entity.Store;
import com.invillia.acme.storeservice.exception.NotFoundStorage;
import com.invillia.acme.storeservice.repository.StoreRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreService(StoreMapper storeMapper, StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    public StoreDTO addStore(StoreDTO storeDTO) {
        Store store = storeMapper.dtoToEntity(storeDTO);
        store.setId(null);
        return storeMapper.entityToDto(storeRepository.save(store));
    }

    public StoreDTO updateStore(String id, StoreDTO storeDTO) {
        Store store = storeMapper.dtoToEntity(storeDTO);
        storeRepository.findById(id).orElseThrow(() -> new NotFoundStorage(id));
        store.setId(id);
        return storeMapper.entityToDto(storeRepository.save(store));
    }

    public List<StoreDTO> findAllByParameters(String address, String name) {
        BooleanExpression predicate = QStore.store.address.containsIgnoreCase(StringUtils.defaultIfBlank(address, StringUtils.EMPTY))
                .and(QStore.store.name.containsIgnoreCase(StringUtils.defaultIfBlank(name, StringUtils.EMPTY)));
        return StreamSupport.stream(storeRepository.findAll(predicate).spliterator(), false).map(storeMapper::entityToDto).collect(Collectors.toList());
    }

}
