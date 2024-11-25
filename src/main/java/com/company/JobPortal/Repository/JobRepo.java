package com.company.JobPortal.Repository;

import com.company.JobPortal.Model.Job.Job;
import com.company.JobPortal.Model.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    // Find all jobs by the user who created them
    List<Job> findByCreatedBy(Users user);

    // Alternatively, find jobs by the user's ID
    List<Job> findByCreatedById(Long userId);

    @Query("SELECT j FROM Job j " +
            "WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY j.date DESC")
    List<Job> searchJobsByKeyword(String keyword);

}
