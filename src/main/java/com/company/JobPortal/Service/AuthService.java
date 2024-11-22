package com.company.JobPortal.Service;

import com.company.JobPortal.DTO.LoginUserDTO;
import com.company.JobPortal.JWT.JWTService;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    public String verifyUser(LoginUserDTO loginUser) {
        Optional<Users> existingUser= userRepo.findByEmail(loginUser.getEmail());

        if(existingUser.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }

        Authentication authentication= authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        if(authentication.isAuthenticated()) {
            System.out.println("User is Authenticated and Token genereration process is started");
            return jwtService.generateToken(existingUser.get());
        }
        return "Details are wrong";
    }
}
