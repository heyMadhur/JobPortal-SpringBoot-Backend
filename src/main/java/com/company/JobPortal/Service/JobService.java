package com.company.JobPortal.Service;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Job updateJob(Job job, Long adminId, Long companyId, Long jobId) {
        Optional<Job> job1= jobRepo.findById(jobId);
        if(job1.isPresent()) {
            job.setCompany(companyService.getCompanyById(companyId));
            job.setCreatedBy(job1.get().getCreatedBy());
            job.setId(job1.get().getId());
        }
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

    public Set<Application> getAllApplications(Long jobId) {
        Optional<Job> job= jobRepo.findById(jobId);
        if(job.isPresent()) {
            return job.get().getApplications();
        }
        return null;
    }
}
