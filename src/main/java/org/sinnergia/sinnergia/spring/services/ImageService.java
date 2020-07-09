package org.sinnergia.sinnergia.spring.services;

import org.sinnergia.sinnergia.spring.exceptions.UnsupportedExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class ImageService {

    Collection<String> extensions;

    Path path;

    public static final String BASIC_PATH = "src/main/resources/files";

    public static final String DEFAULT_IMAGE_URI = "src/main/resources/files/emptyImage.jpg";

    public ImageService() {
        extensions = new ArrayList<>();
        extensions.add("png");
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("svg");
        extensions.add("gif");
    }

    public String getImageExtension(String fileName){
        String[] splitFileName = fileName.split("\\.");
        String extension = splitFileName[splitFileName.length -1];
        if(!extensions.contains(extension)){
            throw new UnsupportedExtension(extension + " extension is not supported.");
        }
        return extension;
    }

    public String getPath(String name, String fileName){
        String extension = getImageExtension(fileName);
        this.path = FileSystems.getDefault().getPath(ImageService.BASIC_PATH, name + "." + extension);
        return this.path.toString();
    }

    public void saveImage(MultipartFile image) throws IOException {
        image.transferTo(path);
    }
}
