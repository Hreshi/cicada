package org.aissms.cicada.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// store documents in mongodb
// current filesystem
@Service
public class MongoFileStorageService {
    @Value("${user.avatar.folder}")
    String avatarFolderName;
    
    public String storeFile(MultipartFile file) {
        File folder = new File(avatarFolderName);
        if(!folder.exists()) {
            folder.mkdir();
        }
        String fileName = file.getOriginalFilename();
        String fileExtension = ".jpeg";
        if (fileName != null && !fileName.isEmpty()) {
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = fileName.substring(dotIndex);
            }
        }
        fileName = getUniqueString() + fileExtension;
        try {
            FileOutputStream outputStream = new FileOutputStream(avatarFolderName + "/" + fileName);
            outputStream.write(file.getBytes());
            outputStream.close();
            System.out.println("Binary file written successfully ");
        } catch (Exception e) {
            System.err.println("Error writing binary file: " + e.getMessage());
        }
        return fileName;
    }
    public Resource loadFileAsResource(String fileName) {
        Path path = Paths.get(avatarFolderName+"/"+fileName);
        File folder = new File(avatarFolderName);
        if(path == null) {
            path = Paths.get(avatarFolderName + "/default.jpg");
        }
        try {
            return new InputStreamResource(Files.newInputStream(path));
        } catch(IOException ex) {
            return null;
        }
    }
    private String getUniqueString() {
        return UUID.randomUUID().toString();
    }
}
