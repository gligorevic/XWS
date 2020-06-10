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

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImages(Map<String, byte[]> images, Long carId) throws IOException {
        System.out.println("Ovoliki je " + images.size());
        String folder = "images/" + carId.toString();
        List<Image> newImages = new ArrayList<>();

        for (Map.Entry<String, byte[]> image : images.entrySet()) {
            Files.createDirectories(Paths.get(folder));
            Path path = Paths.get(folder + image.getKey());
            Files.write(path, image.getValue());
            newImages.add(new Image(carId, image.getKey()));
        }
        System.out.println("Dodao sam sve");
        imageRepository.saveAll(newImages);
        System.out.println("Sacuvao sam sve");
    }
}
