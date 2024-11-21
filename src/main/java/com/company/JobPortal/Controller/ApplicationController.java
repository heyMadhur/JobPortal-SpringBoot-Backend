package com.company.JobPortal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    @GetMapping("/")
    public String defaultMathod(){
        return "Application Controller called";
    }


}
