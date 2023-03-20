package org.aissms.cicada.user;

import java.io.File;

import org.aissms.cicada.mongo.MongoFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired UserService userService;
    @Autowired MongoFileStorageService mongoFileStorageService;
    
    @GetMapping("/info/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto dto = userService.getUserByEmail(email);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
    }

    @GetMapping("/avatar/{email}")
    public ResponseEntity<Resource> getUserAvatar(@PathVariable("email") String email) {
        UserDto dto = userService.getUserByEmail(email);
        Resource resource = mongoFileStorageService.loadFileAsResource(dto.avatarUrl);
        return ResponseEntity.ok().body(resource);
    }
}
