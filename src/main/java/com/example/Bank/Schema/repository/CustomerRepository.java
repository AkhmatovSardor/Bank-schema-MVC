package com.example.Bank.Schema.repository;

import com.example.Bank.Schema.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByCustomerIdAndDeletedAtIsNull(Integer id);

    @Query(value = "select c from Customer c where " +
            "coalesce(:customerId,c.customerId)=c.customerId and " +
            "coalesce(:firstName,c.firstName)=c.firstName and " +
            "coalesce(:lastName,c.lastName)=c.lastName and " +
            "coalesce(:age,c.age)=c.age and " +
            "coalesce(:email,c.email)=c.email and " +
            "coalesce(:password,c.password)=c.password and " +
            "coalesce(:address,c.address)=c.address")
    Page<Customer> findByCustomerByPaginationSearch(
            @Param(value = "customerId") Integer customerId,
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName,
            @Param(value = "age") Integer age,
            @Param(value = "email") String email,
            @Param(value = "password") String password,
            @Param(value = "address") String address,
            Pageable pageable);
    Optional<Customer> findByUsernameAndDeletedAtIsNull(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
