package com.company.JobPortal.DTO;

import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoDTO {

    private Long id;

    private String fullName, email, role, phoneNumber;

    private Profile profile;

    private boolean success;

    public UserInfoDTO(Users user, boolean success) {
        this.id= user.getId();
        this.fullName= user.getFullName();
        this.email= user.getEmail();
        this.role= user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.profile= user.getProfile();
        this.success= success;
    }


}

