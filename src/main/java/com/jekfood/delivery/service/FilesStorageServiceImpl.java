package com.jekfood.delivery.service;

import com.jekfood.delivery.domain.Plates;
import com.jekfood.delivery.repository.PlatesRepository;
import com.jekfood.delivery.web.rest.FilesController;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private PlatesRepository platesRepository;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    /*  @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    
    */

    @Override
    public void save(MultipartFile file) {
        try {
            // Save the file to the storage location
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

            // Get the filename and generate the URL
            String filename = file.getOriginalFilename();
            String url = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", filename).build().toString();

            // Find the corresponding Plates entity based on its identifier (e.g., ID)
            Plates plate = platesRepository.findById("64c9459a44980a79f6bfc532").orElse(null);

            if (plate != null) {
                // Get the current list of photos (URLs) for the product
                List<String> photos = plate.getPhotos();
                if (photos == null) {
                    photos = new ArrayList<>();
                }
                // Add the new URL to the list
                photos.add(url);

                // Update the 'photos' field with the new list of URLs
                plate.setPhotos(photos);
                platesRepository.save(plate);
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
