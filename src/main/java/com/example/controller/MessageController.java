package com.example.controller;

import com.example.dto.request.MessageRequest;
import com.example.dto.response.MessageResponse;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
 @CrossOrigin(origins = "*")
 @RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest){
        MessageResponse messageResponse = messageService.sendMessage(messageRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
            @RequestParam Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Message> messages = messageRepository.findByConversationId(conversationId, pageable);

        List<MessageResponse> responses = messages.stream()
                .map(MessageResponse::fromEntity).collect(Collectors.toList());

        return  ResponseEntity.ok(responses);
    }

}
