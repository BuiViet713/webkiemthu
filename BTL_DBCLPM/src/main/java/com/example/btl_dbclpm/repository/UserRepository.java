package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User,Long>{
    User findByUsernameAndPassword(String username,String password);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT u.fullName FROM User u")
    List<String> findAllFullNames();
}
