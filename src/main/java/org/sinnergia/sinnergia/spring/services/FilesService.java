package org.sinnergia.sinnergia.spring.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesService {
    private String upload_folder = "./src/main/resource/files/";

    public String saveFile(MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload_folder + file.getOriginalFilename());
            Files.write(path, bytes);

            return upload_folder + file.getOriginalFilename();
        }
        return "";
    }
}
