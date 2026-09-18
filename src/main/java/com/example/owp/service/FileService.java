package com.example.owp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "__" + image.getOriginalFilename();

        Path path = Paths.get("uploads/news/" + fileName);

        Files.createDirectories(path.getParent());
        Files.copy(image.getInputStream(),path);

        return "uploads/news/" + fileName;
    }

    public void deleteImage(String imageUrl) throws IOException {
        Path path = Paths.get("." + imageUrl);
        Files.deleteIfExists(path);
    }
}
