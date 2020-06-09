package com.example.SearchService.service;

import com.example.SearchService.domain.Image;
import com.example.SearchService.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImages(MultipartFile[] files, Long carId) throws IOException {
        System.out.println("Ovoliki je " + files.length);
        String folder = "images/" + carId.toString();
        List<Image> images = new ArrayList<>();
        Files.createDirectories(Paths.get(folder));
        for(int i = 0; i < files.length; i++) {
            byte[] bytes = files[i].getBytes();
            Files.createDirectories(Paths.get(folder));
            Path path = Paths.get(folder + files[i].getOriginalFilename());
            Files.write(path, bytes);
            images.add(new Image(carId, files[i].getOriginalFilename()));
        }
        imageRepository.saveAll(images);
    }
}
