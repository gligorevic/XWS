package com.example.AgentApplication.controller;

import com.example.AgentApplication.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{carId}")
    public List<String> getCarImagesUrl(@PathVariable("carId") Long carId) {
        try {
            return imageService.getAllImagesByCarId(carId);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{carId}/{filename}")
    public ResponseEntity<Resource> loadAsResource(@PathVariable("carId") Long carId, @PathVariable("filename") String filename) throws IOException {
        String folder = "images/" + carId + "/";
        byte[] bytes = Files.readAllBytes(Paths.get(folder + filename));

        Resource file = new ByteArrayResource(bytes);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
