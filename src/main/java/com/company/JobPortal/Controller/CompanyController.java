package com.company.JobPortal.Controller;

import com.company.JobPortal.DTO.CompanyInfoDTO;
import com.company.JobPortal.DTO.UserInfoDTO;
import com.company.JobPortal.Model.Company.Company;
import com.company.JobPortal.Model.User.Profile;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Response.CustomResponse;
import com.company.JobPortal.Service.Cloudinary.CloudinaryService;
import com.company.JobPortal.Service.CompanyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CloudinaryService cloudinaryService;


    @GetMapping("/")
    public String defaultMathod(){
        return "Company Controller called";
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCompanies(){
//        System.out.println("Get ALl Company request received");
        List<Company> response= companyService.getAllCompanies();
        CustomResponse<List<Company>> finalResponse= new CustomResponse<>("Companies are Fetched", response, true);
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }

    @PostMapping("/register/{userId}")
    public ResponseEntity<?> createCompany(@RequestBody Company company, @PathVariable Long userId) {
        Company response= companyService.createCompany(userId, company);
        if(response!=null) {
            CustomResponse<Company> finalResponse= new CustomResponse<>("Company registered successfully", response, true);
            return new ResponseEntity<>(finalResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{companyId}/admin/{userId}")
    public ResponseEntity<?> updateCompany(
            @ModelAttribute Company company,
            @RequestPart(value = "file", required = false) MultipartFile photo,
            @PathVariable Long companyId,
            @PathVariable Long userId) throws Exception {
        try{
            if (photo != null && !photo.isEmpty()) {
                Company prev= companyService.getCompanyById(companyId);
                if(prev.getLogoPublicId()!=null) {
                    cloudinaryService.deleteFile(prev.getLogoPublicId());
                }

                Map<String, String> res=cloudinaryService.uploadFile(photo);
                System.out.println("Res= "+res);
                company.setLogoPublicId(res.get("publicId"));
                company.setLogoUrl(res.get("fileUrl"));

            }
            company.setId(companyId);
            Company response= companyService.updateCompany(company, userId);
            if(response!=null) {
                CompanyInfoDTO finalResponse= new CompanyInfoDTO(response);
                System.out.println(finalResponse);
                CustomResponse customResponse= new CustomResponse("Company Information Updated", finalResponse, true);
                return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Error error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long companyId) {
        Company company= companyService.getCompanyById(companyId);
        if(company!=null) {
            CustomResponse<Company> customResponse= new CustomResponse<>("Company Found", company, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
