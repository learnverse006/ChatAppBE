package com.example.service;
import com.example.dto.request.MessageRequest;
import com.example.dto.response.MessageResponse;
import com.example.entity.Conversation;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.ConversationRepository;
import com.example.repository.MessageRepository;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService{

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageResponse sendMessage(MessageRequest request){

        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(request.getContent());

        Message saved = messageRepository.save(message);

        return new MessageResponse(saved.getId(), sender.getFullName(), saved.getContent(), saved.getSentAt());
    }
}