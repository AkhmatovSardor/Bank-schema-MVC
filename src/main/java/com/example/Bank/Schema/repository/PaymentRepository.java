package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.dto.PaymentDto;
import com.example.Bank.Schema.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,Integer> {
    Optional<Payment> findByPaymentIdAndDeletedAtIsNull(Integer id);
}
