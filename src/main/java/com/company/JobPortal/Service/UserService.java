package com.company.JobPortal.Service;

import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserInfoDTO registerUser(Users user) {
        // Check if email already exists
        Optional<Users> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Error("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users response= userRepo.save(user);
        if(response.getId()!=null) {
            return new UserInfoDTO(response, true);
        }
        throw new RuntimeException("User registration failed!");
    }

    public Users updateUser(Users user) {
        return userRepo.save(user);
    }

    public Users findUserByEmail(String email) {
        Optional<Users> user= userRepo.findByEmail(email);
        if(user.isPresent()) return user.get();
        throw new UsernameNotFoundException("UserNotFound");
    }
}
