package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Banker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankerRepository extends CrudRepository<Banker,Integer> {
    Optional<Banker> findByBankerIdAndDeletedAtIsNull(Integer id);
    Boolean existsByBankerName(String name);
}
