package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Borrower;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends CrudRepository<Borrower,Integer> {
    Optional<Borrower>findByBorrowIdAndDeletedAtIsNull(Integer id);
}
