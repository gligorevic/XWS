package com.example.SearchService.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RestController
public class ImageController {

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) {
        try {
            String folder = "images/";
            byte[] bytes = file.getBytes();
            Files.createDirectories(Paths.get(folder));
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);

            return new ResponseEntity<>("Image created", HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("FAil", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/static/images/{filename}")
    public ResponseEntity<Resource> loadAsResource(@PathVariable String filename) throws IOException {
        String folder = "images/";
        byte[] bytes = Files.readAllBytes(Paths.get(folder + filename));

        Resource file = new ByteArrayResource(bytes);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}

