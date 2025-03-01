package com.company.JobPortal.Repository;

import com.company.JobPortal.Model.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email); // Find user by email
//    Optional<Users> findByFullName(String fullName);
}
