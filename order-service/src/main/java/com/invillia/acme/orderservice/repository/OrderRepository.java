package com.invillia.acme.orderservice.repository;


import com.invillia.acme.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepository extends MongoRepository<Order, String>, QuerydslPredicateExecutor<Order> {

}
