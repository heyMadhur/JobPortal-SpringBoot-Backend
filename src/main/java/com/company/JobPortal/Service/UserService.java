package com.company.JobPortal.Service;

import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserInfoDTO registerUser(Users user) {
        // Check if email already exists
        Optional<Users> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Error("Email already exists!");
        }

        Users response= userRepo.save(user);
        if(response.getId()!=null) {
            UserInfoDTO finalResponse= new UserInfoDTO();
            finalResponse.setId(response.getId());
            finalResponse.setFullName(response.getFullName());
            finalResponse.setEmail(response.getEmail());
            finalResponse.setRole(response.getRole());
            finalResponse.setPhoneNumber(response.getPhoneNumber());
            finalResponse.setProfile(response.getProfile());
            finalResponse.setSuccess(true);
            return finalResponse;
        }
        throw new RuntimeException("User registration failed!");
    }

    public Users updateUser(Users user) {
        return userRepo.save(user);
    }
}
