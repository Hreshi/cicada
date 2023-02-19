package org.aissms.cicada.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired StatusService statusService;
    
    @GetMapping("/{email}")
    public ResponseEntity<Status> getStatus(Authentication auth, @PathVariable("email") String email) {
        Status status = statusService.getStatus(auth, email);
        if(status == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
}
