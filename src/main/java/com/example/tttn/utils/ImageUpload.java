package com.example.tttn.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\TTTN\\src\\main\\resources\\static\\productImages";

    public boolean uploadImage(MultipartFile image) {
        boolean isUpload = false;
        try {
            Files.copy(image.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator, image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }
}
