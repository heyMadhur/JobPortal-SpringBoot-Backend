package com.company.JobPortal.DTO;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateProfileDTO {

    private String fullName, email, phoneNumber, bio, resumeUrl, resumeId, resumeOriginalName;
    private List<String> skills= new ArrayList<>();


}
