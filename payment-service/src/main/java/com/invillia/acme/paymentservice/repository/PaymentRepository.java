package com.invillia.acme.paymentservice.repository;

import com.invillia.acme.paymentservice.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findAllByOrder(String order);

}
