package com.diemdanh.factory;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;

@Service
public class FilesStorageService {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private String getRootPath() throws Exception {
        if(isWindows()) {
            return "D:/JavaRootPath/";
        } else if (isUnix()) {
            return "/home/JavaRootPath/";
        } else {
            throw new Exception("Cannot set root path because of Unknow OS");
        }
    }

    public Path convertRelativeToAbsolutePath(String relativePath) throws Exception {
        return Paths.get(getRootPath() + relativePath);
    }

    public void saveAs(MultipartFile file, String relativePath) {
        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
            if (! directory.exists()){
                directory.mkdirs();
            }

            Files.copy(file.getInputStream(), convertRelativeToAbsolutePath(relativePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = convertRelativeToAbsolutePath(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(String filename) {
        try {
            Path file = convertRelativeToAbsolutePath(filename);
            return Files.deleteIfExists(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isWindows() {
        return OS.contains("win");
    }

    private boolean isMac() {
        return OS.contains("mac");
    }

    private boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    private boolean isSolaris() {
        return OS.contains("sunos");
    }

}

