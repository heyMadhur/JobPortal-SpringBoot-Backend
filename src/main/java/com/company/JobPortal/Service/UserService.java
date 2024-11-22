package com.company.JobPortal.Service;

import com.company.JobPortal.DTO.UpdateProfileDTO;
import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import com.company.JobPortal.Service.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @Autowired
    CloudinaryService cloudinaryService;

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

    public Users updateUser(UpdateProfileDTO updateProfileDTO) {
        Users user= findUserByEmail(updateProfileDTO.getEmail());

        setProfileDetails(user, updateProfileDTO);

        return userRepo.save(user);
    }

    private void setProfileDetails(Users user, UpdateProfileDTO updateProfileDTO) {
        Profile profile= user.getProfile();
        user.setFullName(updateProfileDTO.getFullName());
        user.setEmail(updateProfileDTO.getEmail());
        profile.setBio(updateProfileDTO.getBio());
        user.setPhoneNumber(updateProfileDTO.getPhoneNumber());
        profile.setSkills(updateProfileDTO.getSkills());
        if(updateProfileDTO.getResumeUrl()!=null) {
            if(profile.getResumeId()!=null) {
                cloudinaryService.deleteFile(profile.getResumeId());
            }
            profile.setResumeId(updateProfileDTO.getResumeId());
            profile.setResumeUrl(updateProfileDTO.getResumeUrl());
            profile.setResumeOriginalName(updateProfileDTO.getResumeOriginalName());
        }
        user.setProfile(profile);
    }

    public Users findUserByEmail(String email) {
        Optional<Users> user= userRepo.findByEmail(email);
        if(user.isPresent()) return user.get();
        throw new UsernameNotFoundException("UserNotFound");
    }
}
