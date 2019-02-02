package com.invillia.acme.storeservice.repository;

import com.invillia.acme.storeservice.entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StoreRepository extends MongoRepository<Store, String>, QuerydslPredicateExecutor<Store> {

}
