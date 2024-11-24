package com.company.JobPortal.Model.Company;

import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName, description, website, location, logoUrl, logoPublicId;

    private Date date= new Date();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Users user;

}
