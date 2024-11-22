package com.company.JobPortal.Controller;

import com.company.JobPortal.DTO.LoginUserDTO;
import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Service.AuthService;
import com.company.JobPortal.Service.Cloudinary.CloudinaryService;
import com.company.JobPortal.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
    AuthService authService;

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

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> loginUser(@RequestBody LoginUserDTO loginUser, HttpServletResponse response) {
        String jwtToken= authService.verifyUser(loginUser);

        // Add the token as a cookie
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true); // Prevent client-side JavaScript from accessing the cookie
        cookie.setSecure(true); // Use 'true' if your app is served over HTTPS
        cookie.setPath("/"); // Path to make the cookie available across your app
        cookie.setDomain("localhost"); // Replace with your domain if deployed

        // Add the cookie to the response
        response.addCookie(cookie);
        UserInfoDTO user= new UserInfoDTO(userService.findUserByEmail(loginUser.getEmail()), true);
        CustomResponse customResponse= new CustomResponse("Welcome " + user.getFullName(), user, true);

        return new ResponseEntity<>(customResponse, HttpStatus.ACCEPTED);

    }

    @AllArgsConstructor
    @Getter
    @Setter
    static
    class CustomResponse {
        String message;
        UserInfoDTO user;
        boolean success;
    }


}

