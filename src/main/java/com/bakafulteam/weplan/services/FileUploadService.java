package com.bakafulteam.weplan.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadService {

    public static String getFileDirectory(String fileFolder, String fileName) {
        return System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources" +
                File.separator + "static" +
                File.separator + fileFolder +
                File.separator + fileName;
    }

    public static void uploadFile(String fileFolder, MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        Path path = Paths.get(getFileDirectory(fileFolder, file.getOriginalFilename()));
        Files.write(path, fileBytes);
    }
}
