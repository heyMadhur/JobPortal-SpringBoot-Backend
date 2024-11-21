package com.company.JobPortal.Model.User;

import com.company.JobPortal.Model.Company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio, resumeUrl, resumeId, resumeOriginalName, profilePhotoUrl, profilePhotoId;
    private List<String> skills;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Company> company;
}
