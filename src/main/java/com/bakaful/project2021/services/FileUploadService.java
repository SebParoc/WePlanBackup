package com.bakaful.project2021.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    public void uploadFile(MultipartFile image) throws IOException {
        image.transferTo(new File(System.getProperty("user.dir"
                + "/src/main/resources/static/profile_pictures"
                    + image.getOriginalFilename())));
    }
}
