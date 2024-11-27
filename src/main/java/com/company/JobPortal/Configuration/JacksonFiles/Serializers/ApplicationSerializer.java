package com.company.JobPortal.Configuration.JacksonFiles.Serializers;

import com.company.JobPortal.Model.Appication.Application;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ApplicationSerializer extends JsonSerializer<Application> {
    @Override
    public void serialize(Application application, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", application.getId());
        gen.writeObjectField("date", application.getDate());
        gen.writeStringField("applicationStatus", application.getApplicationStatus().toString());

        gen.writeObjectFieldStart("applicant");
        gen.writeObjectField("id", application.getApplicant().getId());
        gen.writeObjectField("fullName", application.getApplicant().getFullName());
        gen.writeObjectField("role", application.getApplicant().getRole());
        gen.writeObjectField("phoneNumber", application.getApplicant().getPhoneNumber());
        gen.writeObjectField("email", application.getApplicant().getEmail());
            gen.writeObjectFieldStart("profile");
                gen.writeObjectField("resumeUrl", application.getApplicant().getProfile().getResumeUrl());
                gen.writeObjectField("resumeOriginalName", application.getApplicant().getProfile().getResumeOriginalName());
            gen.writeEndObject();
        gen.writeEndObject();

        gen.writeObjectFieldStart("job");
            gen.writeObjectField("id", application.getJob().getId());
            gen.writeStringField("title", application.getJob().getTitle());
            gen.writeObjectFieldStart("company");
                gen.writeStringField("companyName", application.getJob().getCompany().getCompanyName());
            gen.writeEndObject();
        gen.writeEndObject();
        gen.writeEndObject();


    }
}
