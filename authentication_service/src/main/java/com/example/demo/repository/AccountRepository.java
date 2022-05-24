package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT a.id, a.full_name, a.username, a.password, a.role_id, a.is_active FROM account a WHERE a.is_active = 1 AND a.username = :username", nativeQuery = true)
    Optional<Account> findByUsername(@Param("username") String username);
}
