package com.codegym.bookstore_management.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadService {
    @Value("${app.images.upload-root}")
    private String imageUploadRoot;

    public String uploadFile(MultipartFile file, String subFolder) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);

            String newFileName = UUID.randomUUID().toString() + fileExtension;

            File uploadPath = buildUploadPath(subFolder);

            createDirectoryIfNotExists(uploadPath);

            File destFile = new File(uploadPath, newFileName);
            file.transferTo(destFile);

            return "/images/" + subFolder + "/" + newFileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            int lastDotIndex = filename.lastIndexOf(".");
            return filename.substring(lastDotIndex);
        }
        return "";
    }

    private File buildUploadPath(String subFolder) {
        return new File(imageUploadRoot + subFolder + File.separator);
    }

    private void createDirectoryIfNotExists(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}