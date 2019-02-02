package com.invillia.acme.storeservice.service;

import com.invillia.acme.storeservice.dto.StoreDTO;
import com.invillia.acme.storeservice.dto.mappers.StoreMapper;
import com.invillia.acme.storeservice.entity.QStore;
import com.invillia.acme.storeservice.entity.Store;
import com.invillia.acme.storeservice.exception.NotFoundStorage;
import com.invillia.acme.storeservice.repository.StoreRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.sql.rowset.Predicate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreMapper storeMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_addStore() {
        when(storeMapper.dtoToEntity(any())).thenReturn(new Store());
        storeService.addStore(new StoreDTO());
        Mockito.verify(storeMapper, times(1)).entityToDto(any());
        Mockito.verify(storeRepository, times(1)).save(any());
    }

    @Test
    public void should_updateStore() {
        when(storeMapper.dtoToEntity(any())).thenReturn(new Store());
        when(storeRepository.findById("1234")).thenReturn(Optional.of(new Store()));
        storeService.updateStore("1234", new StoreDTO());
        Mockito.verify(storeMapper, times(1)).entityToDto(any());
        Mockito.verify(storeRepository, times(1)).save(any());
    }

    @Test
    public void should_exceptionUpdateStore() {
        thrown.expect(NotFoundStorage.class);
        thrown.expectMessage("Not found storage id 1234");
        when(storeMapper.dtoToEntity(any())).thenReturn(new Store());
        storeService.updateStore("1234", new StoreDTO());
        Mockito.verifyZeroInteractions(storeRepository.save(any()));
    }

    @Test
    public void should_findAllByParameters() {
        BooleanExpression predicate = QStore.store.address.containsIgnoreCase(StringUtils.defaultIfBlank("address", StringUtils.EMPTY))
                .and(QStore.store.name.containsIgnoreCase(StringUtils.defaultIfBlank("name", StringUtils.EMPTY)));
        storeService.findAllByParameters("address", "name");
        Mockito.verify(storeRepository).findAll(predicate);

    }
}