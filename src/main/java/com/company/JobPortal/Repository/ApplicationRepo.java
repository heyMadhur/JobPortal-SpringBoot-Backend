package com.company.JobPortal.Repository;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {

//    List<Application> findByApplicant(Users user);
//    List<Application> findAllByApplicantId(Long userId);
//    @Query(value = "SELECT a FROM Application a WHERE a.applicant.id = :userId")
    Set<Application> findAllByApplicantId(Long userId);
}
