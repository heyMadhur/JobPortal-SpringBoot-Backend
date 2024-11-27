package com.company.JobPortal.Configuration.JacksonFiles;

import com.company.JobPortal.Configuration.JacksonFiles.Serializers.ApplicationSerializer;
import com.company.JobPortal.Configuration.JacksonFiles.Serializers.JobSerializer;
import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Job.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module= new SimpleModule();

//        Register Custom Serializers here
        module.addSerializer(Job.class, new JobSerializer());
        module.addSerializer(Application.class, new ApplicationSerializer());

        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
