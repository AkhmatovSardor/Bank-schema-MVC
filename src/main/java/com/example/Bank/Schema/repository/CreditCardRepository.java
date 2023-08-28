package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {
    Optional<CreditCard> findByCreditCardIdAndDeletedAtIsNull(Integer id);
    Boolean existsByNumber(Long number);
}
