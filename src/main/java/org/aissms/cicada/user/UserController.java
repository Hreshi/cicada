package org.aissms.cicada.user;

import org.aissms.cicada.mongo.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired UserService userService;
    @Autowired FileService mongoFileStorageService;
    
    @GetMapping("/info/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto dto = userService.getUserByEmail(email);
        if(dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
    }
    @GetMapping("/myself")
    public ResponseEntity<UserDto> getMyself(Authentication auth) {
        return new ResponseEntity<UserDto>(userService.getUserByEmail(auth.getName()), HttpStatus.OK);
    }
}
