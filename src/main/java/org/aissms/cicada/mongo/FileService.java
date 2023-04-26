package org.aissms.cicada.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import java.io.IOException;
import java.util.UUID;

// store image with name = unique random string
// name will be used as file name in mongodb
@Service
public class FileService {
    
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired GridFsOperations gridFsOperations;

    public String storeFile(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            gridFsTemplate.store(file.getInputStream(), fileName);
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return fileName;
    }
    public Resource loadFileAsResource(String fileName) {
        return  gridFsTemplate.getResource(fileName);
    }
    private String getUniqueString() {
        return UUID.randomUUID().toString();
    }
    private String generateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        System.out.println(originalFileName + " : original");
        int index = originalFileName.lastIndexOf(".");
        String extention = ".png";
        if(index >= 0) {
            extention = originalFileName.substring(index);
        }
        
        return getUniqueString() + extention;
    }
    public void deleteFile(String filename) {
        // First, find the file by its filename
        Query query = new Query();
        query.addCriteria(Criteria.where("filename").is(filename));
        GridFSFile file = gridFsTemplate.findOne(query);
    
        if (file != null) {
            // If the file exists, delete it using the GridFsOperations#delete method
            gridFsOperations.delete(query);
    
            // Alternatively, you can delete the file by its ObjectId using GridFsOperations#deleteById
            // gridFsOperations.deleteById(file.getObjectId());
    
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("File not found.");
        }
    }
}
