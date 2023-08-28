package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
