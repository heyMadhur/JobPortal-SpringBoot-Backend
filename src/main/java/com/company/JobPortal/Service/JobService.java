package com.company.JobPortal.Service;

import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    public List<Job> getAllJobsByAdmin(Long adminId) {
        return jobRepo.findByCreatedById(adminId);
    }

    public Job postJob(Job job,  Long adminId, Long companyId) {
        job.setCompany(companyService.getCompanyById(companyId));
        job.setCreatedBy(userService.getUserById(adminId));
        System.out.println("Job before Saving= "+job);
        return jobRepo.save(job);
    }

    public Job getJobById(Long jobId) {
        Optional<Job> job= jobRepo.findById(jobId);
        if (job.isPresent()) {
            return job.get();
        }
        return null;
    }

    public List<Job> getAllSearchedJobs(String keyword) {
        if(keyword.isEmpty()) {
            return jobRepo.findAll();
        }
        else {
            return jobRepo.searchJobsByKeyword(keyword);
        }
    }
}
