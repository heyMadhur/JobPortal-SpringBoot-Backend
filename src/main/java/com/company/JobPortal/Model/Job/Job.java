package com.company.JobPortal.Model.Job;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Company.Company;
import com.company.JobPortal.Model.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
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

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", unique = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "created_by_id", referencedColumnName = "id", unique = false)
    private Users createdBy;

    @ElementCollection
    private Set<String> requirements;

    private Date date= new Date();

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Application> applications= new HashSet<>();

}
