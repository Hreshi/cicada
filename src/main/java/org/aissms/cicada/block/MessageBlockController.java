package org.aissms.cicada.block;

import org.aissms.cicada.messaging.MyMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/{email}")
public class MessageBlockController {
    @Autowired MessageBlockService service;

    @GetMapping("/block-count")
    public ResponseEntity<Integer> getBlockCount(Authentication auth, @PathVariable("email") String email) {
        int size = service.getBlockCount(auth.getName(), email);
        if(size < 0) {
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Integer>(size, HttpStatus.OK);
    }

    // blocks are 1 indexed
    @GetMapping("/block/{index}")
    public ResponseEntity<MessageBlockDto> getMessageBlock(Authentication auth, @PathVariable Integer index, @PathVariable("email") String email) {
        MessageBlockDto block = service.getMessageBlockDto(auth.getName(), email, index);
        if(block == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<MessageBlockDto>(block, HttpStatus.OK);
    }

    @GetMapping("/last-message")
    public ResponseEntity<MyMessageDto> getLastMessage(Authentication auth, @PathVariable("email") String email) {
        return ResponseEntity.ok(service.getLastMessage(auth.getName(), email));
    }
}
