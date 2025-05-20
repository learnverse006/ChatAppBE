package com.example.controller;

import com.example.dto.request.MessageRequest;
import com.example.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.MessageService;

@RestController
@RequestMapping("/api/messages")
 @CrossOrigin(origins = "*")
 @RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest){
        MessageResponse messageResponse = messageService.sendMessage(messageRequest);
        return ResponseEntity.ok(messageResponse);
    }

}
