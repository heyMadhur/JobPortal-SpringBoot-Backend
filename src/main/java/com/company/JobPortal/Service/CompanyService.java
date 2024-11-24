package com.company.JobPortal.Service;

import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.Company.Company;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.CompanyRepo;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    UserRepo userRepo;

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company createCompany(Long userId, Company company) {
        return companyRepo.save(company);
    }

    public Company updateCompany(Company company, Long userId) {
        Optional<Users> user= userRepo.findById(userId);
        if(user.isPresent()) {
            company.setUser(user.get());
        }
        return companyRepo.save(company);
    }

    public Company getCompanyById(Long companyId) {
        Optional<Company> company = companyRepo.findById(companyId);
        if(company.isPresent()) {
            return company.get();
        }
        return null;

    }
}
