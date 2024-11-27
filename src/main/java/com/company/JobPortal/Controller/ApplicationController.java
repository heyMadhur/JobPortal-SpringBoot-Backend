package com.company.JobPortal.Controller;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Repository.JobRepo;
import com.company.JobPortal.Response.CustomResponse;
import com.company.JobPortal.Service.ApplicationService;
import com.company.JobPortal.Service.JobService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    JobService jobService;

    @GetMapping("/")
    public String defaultMathod(){
        return "Application Controller called";
    }

    @GetMapping("/apply/{jobId}/user/{userId}")
    public ResponseEntity<?> applyForJob(@PathVariable Long jobId, @PathVariable Long userId) {
        System.out.println("Apply for Job with Id= "+jobId);
        Job response= applicationService.applyForJob(jobId, userId);
        if(response!=null) {
            CustomResponse<Job> customResponse= new CustomResponse<>("Job Applied", response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-all-applied-jobs/user/{userId}")
    public ResponseEntity<?> getAllAppliedJobs(@PathVariable Long userId) {
        System.out.println("GetAllAppliedJobs Request Reached");
        Set<Application> response= applicationService.getAllAppliedJobs(userId);
        System.out.println("All Applied Jobs= "+response);
//        System.out.println("Reach 2");
        if(response!=null) {
//            System.out.println("Reach 3");
            CustomResponse<Set<Application>> customResponse= new CustomResponse<>("All Applied Jobs Found", response, true);
//            System.out.println("cR= "+customResponse.isSuccess());
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getapplicants/{jobId}")
    public ResponseEntity<?> getAllApplicants(@PathVariable Long jobId) {
        Set<Application> response= jobService.getAllApplications(jobId);
        if(response!=null) {
            CustomResponse<Set<Application>> customResponse= new CustomResponse<>("All Applied Jobs Found", response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/status/update/{applicationId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId, @RequestBody Status status) {
        System.out.println("Status= "+status.getStatus());
        System.out.println("userId= "+applicationId);

        Application response= applicationService.updateStatus(applicationId, status.getStatus());
        if(response!=null) {
            CustomResponse<Application> customResponse= new CustomResponse<>("Application "+response.getApplicationStatus(), response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Status{
        private String status;
    }


}
