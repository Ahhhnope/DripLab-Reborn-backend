package com.example.cafe.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/IMG/";

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            String ext = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID() + "." + ext;

            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.write(path, file.getBytes());

            return ResponseEntity.ok(Map.of("imageUrl", "/IMG/" + filename));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Upload thất bại: " + e.getMessage()));
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "jpg";
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}