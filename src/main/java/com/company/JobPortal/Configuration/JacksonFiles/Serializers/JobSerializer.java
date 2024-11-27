package com.company.JobPortal.Configuration.JacksonFiles.Serializers;

import com.company.JobPortal.Model.Appication.Application;
import com.company.JobPortal.Model.Job.Job;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JobSerializer extends JsonSerializer<Job> {

    @Override
    public void serialize(Job job, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", job.getId());
        gen.writeStringField("title", job.getTitle());
        gen.writeStringField("location", job.getLocation());
        gen.writeStringField("jobType", job.getJobType());
        gen.writeNumberField("experienceLevel", job.getExperienceLevel());
        gen.writeNumberField("salary", job.getSalary());
        gen.writeStringField("description", job.getDescription());

        // Include only necessary fields
        if (job.getCompany() != null) {
            gen.writeObjectFieldStart("company");
            gen.writeNumberField("id", job.getCompany().getId());
            gen.writeStringField("companyName", job.getCompany().getCompanyName());
            gen.writeStringField("logoUrl", job.getCompany().getLogoUrl());
            gen.writeEndObject();
        }  else {
            gen.writeNullField("company");
        }

        if (job.getCreatedBy() != null) {
            gen.writeObjectFieldStart("createdBy");
            gen.writeNumberField("id", job.getCreatedBy().getId());
            gen.writeStringField("username", job.getCreatedBy().getFullName());
            gen.writeEndObject();
        } else {
            gen.writeNullField("createdBy");
        }

        gen.writeArrayFieldStart("requirements");
        for (String requirement : job.getRequirements()) {
            gen.writeString(requirement);
        }
        gen.writeEndArray();

        // Serialize applications as a list of IDs to avoid circular references
        gen.writeArrayFieldStart("applications");
        for (Application application : job.getApplications()) {
            gen.writeStartObject();
                gen.writeNumberField("id", application.getId());
                gen.writeStringField("applicationStatus", application.getApplicationStatus().toString());

                gen.writeObjectFieldStart("applicant");
                    gen.writeObjectField("id", application.getApplicant().getId());
                    gen.writeStringField("fullName", application.getApplicant().getFullName());
                    gen.writeStringField("email", application.getApplicant().getEmail());
                gen.writeEndObject();
            // Add other fields you need from the application
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
