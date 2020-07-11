package com.example.AgentApplication.service;


import com.example.AgentApplication.domain.Image;
import com.example.AgentApplication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
        return images.stream().map(image -> "/images/" + carId + "/" + image).collect(Collectors.toList());
    }

    public Map<String, byte[]> getImages(Long carId){
        Map<String, byte[]> map = new HashMap<>();
        List<String> images = imageRepository.findImagesByCarId(carId);
        images.stream().forEach(image ->{
            String folder = "images/" + carId + "/";
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(folder + image));
                map.put(image, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return  map;
    }
}
