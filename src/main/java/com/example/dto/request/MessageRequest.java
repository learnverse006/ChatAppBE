package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Long conversationId;
    private Long senderId;
    private String content;
}
