package com.company.JobPortal.Service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary () {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvfq9jqrf",
                "api_key", "197128944335574",
                "api_secret", "yqi7xh6WZpZuPPoEMpiB8-sOjyE"
        ));
    }

}
