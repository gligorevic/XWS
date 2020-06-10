package com.example.ImageService.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ImageController {

//    @PostMapping("/upload/image")
//    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) {
//        try {
//            String folder = "images/";
//            byte[] bytes = file.getBytes();
//            Files.createDirectories(Paths.get(folder));
//            Path path = Paths.get(folder + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//            return new ResponseEntity<>("Image created", HttpStatus.CREATED);
//        } catch(Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>("FAil", HttpStatus.BAD_REQUEST);
//        }
//
//    }

    @GetMapping("/static/images/{carId}/{filename}")
    public ResponseEntity<Resource> loadAsResource(@PathVariable("carId") Long carId, @PathVariable("filename") String filename) throws IOException {
        String folder = "images/" + carId + "/";
        byte[] bytes = Files.readAllBytes(Paths.get(folder + filename));

        Resource file = new ByteArrayResource(bytes);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
