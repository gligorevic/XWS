package com.example.ImageService.service;


import com.example.ImageService.domain.Image;
import com.example.ImageService.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImages(Map<String, byte[]> images, Long carId) throws IOException {
        String folder = "images/" + carId.toString() + "/";
        List<Image> newImages = new ArrayList<>();
        Files.createDirectories(Paths.get(folder));
        for (Map.Entry<String, byte[]> image : images.entrySet()) {
            Path path = Paths.get(folder + image.getKey());
            Files.write(path, image.getValue());
            newImages.add(new Image(carId, image.getKey()));
        }

        imageRepository.saveAll(newImages);
    }

    public List<String> getAllImagesByCarId(Long carId) {
        List<String> images = imageRepository.findImagesByCarId(carId);
        return images.stream().map(image -> "/static/images/" + carId + "/" + image).collect(Collectors.toList());
    }
}
