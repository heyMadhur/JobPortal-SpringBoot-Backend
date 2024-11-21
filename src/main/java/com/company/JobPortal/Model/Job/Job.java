package com.company.JobPortal.Model.Job;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Company.Company;
import com.company.JobPortal.Model.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title, description, location, jobType;

    private int experienceLevel, position, salary;

    @OneToOne
    private Company company;

    @OneToOne
    private Users createdBy;

    private Set<String> requirements;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Application> applications;

}
