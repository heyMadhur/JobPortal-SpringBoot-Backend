package com.company.JobPortal.Controller;

import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Service.Cloudinary.CloudinaryService;
import com.company.JobPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute Users user,
            @RequestPart(value = "file", required = false)MultipartFile photo) throws Exception {
        try{
            if (photo != null && !photo.isEmpty()) {
                Map<String, String> res=cloudinaryService.uploadFile(photo);

                Profile profile= new Profile();
                profile.setProfilePhotoId(res.get("publicId"));
                profile.setProfilePhotoUrl(res.get("fileUrl"));

                user.setProfile(profile);
            }

            UserInfoDTO response= userService.registerUser(user);
            if(response!=null) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Error error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

}
