package com.example.AgentApplication.controller;

import com.example.AgentApplication.dto.MessageDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO){
        try {
            return new ResponseEntity<>(messageService.sendMessage(messageDTO), HttpStatus.CREATED );
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getRecievedMessages(@PathVariable String email){
        try{
            return new ResponseEntity<>(messageService.getMessagesByReciever(email), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<?> getMessagesByRequest(@PathVariable Long id){
        try{
            return new ResponseEntity<>(messageService.getMessagesByRequest(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
