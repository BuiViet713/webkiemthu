package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c.id FROM Customer c WHERE c.fullName = :fullName")
//    @Query("SELECT c.id FROM Customer c JOIN User u ON c.id = u.id WHERE u.fullName = :fullName")
    Long findCustomerIdByFullName(@Param("fullName") String fullName);
}
