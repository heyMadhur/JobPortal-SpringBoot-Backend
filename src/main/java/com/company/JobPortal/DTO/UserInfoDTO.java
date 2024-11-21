package com.company.JobPortal.DTO;

import com.company.JobPortal.Model.User.Profile;
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


}

