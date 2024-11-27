package com.company.JobPortal.Service;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Appication.ApplicationStatus;
import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.ApplicationRepo;
import com.company.JobPortal.Repository.JobRepo;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApplicationService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApplicationRepo  applicationRepo;

    public Job applyForJob(Long jobId, Long userId) {
        Optional<Job> job= jobRepo.findById(jobId);
        Optional<Users> user= userRepo.findById(userId);


        System.out.println("In Job Service Layer");
        System.out.println("jobPresent= "+job.isPresent());
        System.out.println("userPresent= "+user.isPresent());

        if(job.isPresent() && user.isPresent()) {
//          Check if already applied for same job
            for(Application application: job.get().getApplications()) {
                if(application.getApplicant().getId().equals(userId)) {
                    System.out.println("User already applied");
                    return null;
                }
            }
            Application application= new Application();
            application.setApplicant(user.get());
            application.setJob(job.get());
            application.setApplicationStatus(ApplicationStatus.PENDING);

            Set<Application> applications= job.get().getApplications();
            applications.add(application);
            job.get().setApplications(applications);
            applicationRepo.save(application);
            return jobRepo.save(job.get());
        }
        return null;

    }

    public Set<Application> getAllAppliedJobs(Long userId) {
        Set<Application> list= applicationRepo.findAllByApplicantId(userId);
        System.out.println("List= "+list);
        return list;
    }

    public Application updateStatus(Long applicationId, String status) {
        Application application= applicationRepo.findById(applicationId).orElse(null);
        if(application!=null) {
            application.setApplicationStatus(ApplicationStatus.valueOf(status.toUpperCase()));
            return applicationRepo.save(application);
        }
        return application;

    }
}
