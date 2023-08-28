package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
    Optional<Transaction> findByTransactionIdAndDeletedAtIsNull(Integer id);
}
