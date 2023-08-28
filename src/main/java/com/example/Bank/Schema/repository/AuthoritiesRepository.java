package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities,Integer> {
}
