package com.company.JobPortal.Controller;

import com.company.JobPortal.Model.Company.Company;
import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Response.CustomResponse;
import com.company.JobPortal.Service.CompanyService;
import com.company.JobPortal.Service.JobService;
import com.company.JobPortal.Service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String defaultMethod(@RequestParam String keyword){
        return "Job Controller called";
    }

    @GetMapping("/getadminjobs/{adminId}")
    public ResponseEntity<?> getAllAdminJobs(@PathVariable Long adminId) {
        List<Job> response= jobService.getAllJobsByAdmin(adminId);
        if(response!=null) {
            CustomResponse<List<Job>> finalResponse= new CustomResponse<>("Fetched all Jobs", response, true);
            return new ResponseEntity<>(finalResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/post/admin/{adminId}")
    public ResponseEntity<?> postJob(@PathVariable Long adminId, @RequestBody JobPost jobPost){
        Job job= jobPost.convertToJob(jobPost);
        Job response= jobService.postJob(job, adminId, jobPost.getCompanyId());
        if(response!=null) {
            CustomResponse<Job> customResponse= new CustomResponse<>("Job Posted", response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/update/{jobId}/admin/{adminId}")
    public ResponseEntity<?> updateJob(@PathVariable Long jobId, @PathVariable Long adminId, @RequestBody JobPost jobPost){
        System.out.println("Update Job Request Recieived");
        Job job= jobPost.convertToJob(jobPost);
        Job response= jobService.updateJob(job, adminId, jobPost.getCompanyId(), jobId);
        if(response!=null) {
            CustomResponse<Job> customResponse= new CustomResponse<>("Job Posted", response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/get/{jobId}")
    public ResponseEntity<?> getJobById(@PathVariable Long jobId) {
        Job response= jobService.getJobById(jobId);
        if(response!= null) {
            CustomResponse<Job> finalResponse= new CustomResponse<>("Job Found", response, true);
            return new ResponseEntity<>(finalResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user/get-all")
    public ResponseEntity<?> getAllJobsForUser(@RequestParam String keyword) {
        List<Job> response= jobService.getAllSearchedJobs(keyword);
        if(response!=null) {
            CustomResponse<List<Job>> customResponse= new CustomResponse<>("Got All Searched Jobs", response, true);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class JobPost {

        private String title, description, location, jobType;
        private Set<String> requirements;
        private int salary, experienceLevel, position;
        private Long companyId;

        public Job convertToJob(JobPost jobPost) {
            Job job= new Job();
            job.setTitle(jobPost.getTitle());
            job.setDescription(jobPost.getDescription());
            job.setLocation(jobPost.getLocation());
            job.setJobType(jobPost.getJobType());
            job.setExperienceLevel(jobPost.getExperienceLevel());
            job.setPosition(jobPost.getPosition());
            job.setSalary(jobPost.getSalary());
            job.setRequirements(jobPost.getRequirements());
            return job;
        }

    }

}
