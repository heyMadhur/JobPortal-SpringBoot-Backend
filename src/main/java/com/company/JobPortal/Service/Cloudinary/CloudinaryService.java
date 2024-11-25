package com.company.JobPortal.Service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String,String> uploadFile(MultipartFile file) throws Exception {
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            Map<String, String> myRes= new HashMap<>();

            System.out.println("Upload Result= "+uploadResult);

            myRes.put("publicId", (String) uploadResult.get("public_id"));
            myRes.put("fileUrl", (String) uploadResult.get("url"));

            return myRes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Not Uploaded");
        }

    }

    public Map deleteFile(String publicId) {
        System.out.println("Delete File is called");
        try {
            Map response= cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            Map<String, String> myRes= new HashMap<>();

            myRes.put("publicId", (String) response.get("public_id"));
            myRes.put("fileUrl", (String) response.get("url"));
            return myRes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
