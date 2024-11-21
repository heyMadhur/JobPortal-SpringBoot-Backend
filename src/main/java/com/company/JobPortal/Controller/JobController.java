package com.company.JobPortal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    @GetMapping("/")
    public String defaultMathod(){
        return "Job Controller called";
    }

}
