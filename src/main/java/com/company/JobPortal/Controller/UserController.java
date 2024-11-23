package com.company.JobPortal.Controller;

import com.company.JobPortal.DTO.UpdateProfileDTO;
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
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("/")
    public String defaultMethod(){
        return "User Controller called";
    }


    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            @ModelAttribute UpdateProfileDTO updateProfileDTO,
            @RequestPart(value = "file", required = false) MultipartFile resume) {

        if (resume != null && !resume.isEmpty()) {
            Map<String, String> res= null;
            try {
                res = cloudinaryService.uploadFile(resume);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            updateProfileDTO.setResumeId(res.get("publicId"));
            updateProfileDTO.setResumeUrl(res.get("fileUrl"));
            updateProfileDTO.setResumeOriginalName(resume.getOriginalFilename());
        }

//        System.out.println("Updated Profile DTO= "+updateProfileDTO);

        Users response= userService.updateUser(updateProfileDTO);
        if(response!=null) {
            UserInfoDTO finalResponse= new UserInfoDTO(response, true);
            System.out.println("Final Response= "+finalResponse);
            return new ResponseEntity<>(finalResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    


}
