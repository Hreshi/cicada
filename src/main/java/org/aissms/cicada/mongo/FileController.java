package org.aissms.cicada.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REQUEST_PATH = mapping of file controller 
// if not set files won't be served
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/files")
public class FileController {
    
    public static String REQUEST_PATH = "/api/files/";

    @Autowired
    FileService fileService;
    
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);
        return ResponseEntity.ok(resource);
    }
}
