package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends CrudRepository<Loan,Integer> {
    Optional<Loan> findByLoanIdAndDeletedAtIsNull(Integer id);
}
