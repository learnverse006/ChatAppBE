package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private Long messageId;
    private String senderId;
    private String content;
    private Timestamp timestamp;
}
