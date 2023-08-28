package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Integer> {
    Optional<Branch> findByBranchIdAndDeletedAtIsNull(Integer id);
    Boolean existsByAddress(String address);
}
