package com.company.JobPortal.Controller;

import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String defaultMathod(){
        return "User Controller called";
    }


    @PostMapping("/profile/update")
    public ResponseEntity<Users> updateProfile(@RequestBody Users user) {
        Users response= userService.updateUser(user);
        if(response!=null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    


}
