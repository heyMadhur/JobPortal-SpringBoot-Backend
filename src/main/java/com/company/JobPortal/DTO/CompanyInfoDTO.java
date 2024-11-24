package com.company.JobPortal.DTO;

import com.company.JobPortal.Model.Company.Company;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyInfoDTO {
    private Long id;
    private String companyName, description, website, location, logoUrl, logoPublicId;

    private Date date;
    UserInfoDTO userInfoDTO;

    public CompanyInfoDTO(Company company) {
        this.id= company.getId();
        this.companyName= company.getCompanyName();
        this.description= company.getDescription();
        this.website= company.getWebsite();
        this.location= company.getLocation();
        this.logoUrl= company.getLogoUrl();
        this.logoPublicId= company.getLogoPublicId();
        this.date= company.getDate();
        this.userInfoDTO= new UserInfoDTO(company.getUser(),true);
    }

}
