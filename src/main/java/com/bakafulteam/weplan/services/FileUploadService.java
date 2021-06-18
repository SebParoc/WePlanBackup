package com.bakafulteam.weplan.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadService {

    public static String getFileDirectory(String fileName) {
        return System.getProperty("user.home") +
                File.separator + "WePlanFiles" +
                File.separator + fileName;
    }

    public static void uploadFile(String fileName, MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        Path path = Paths.get(getFileDirectory(fileName));
        Files.write(path, fileBytes);
    }
}
