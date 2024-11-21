package com.company.JobPortal.Service;

import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    public Users registerUser(Users user) {
        return userRepo.save(user);
    }
}
