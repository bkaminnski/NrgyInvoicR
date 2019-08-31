package com.hclc.nrgyinvoicr.backend.users.control;

import com.hclc.nrgyinvoicr.backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
