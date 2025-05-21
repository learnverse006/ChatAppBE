package com.example.dto.response;

import com.example.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long messageId;
    private String senderId;
    private String content;
    private Timestamp createdAt;

    public static MessageResponse fromEntity(Message message) {
        MessageResponse dto = new MessageResponse();
        dto.setMessageId(message.getId()); // Đúng tên field
        dto.setSenderId(message.getSender().getId().toString()); // senderId là String
        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }

}
