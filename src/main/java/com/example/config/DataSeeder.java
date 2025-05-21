package com.example.config;

import com.example.entity.Conversation;
import com.example.entity.ConversationMember;
import com.example.entity.User;
import com.example.enums.ConversationType;
import com.example.repository.ConversationMemberRepository;
import com.example.repository.ConversationRepository;
import com.example.repository.MessageRepository;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner    seedData(
            UserRepository userRepo,
            ConversationRepository convoRepo,
            ConversationMemberRepository memberRepo,
            MessageRepository messageRepo
    ) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                // 1. Tạo user
                User alice = new User(null, "alice@example.com", "123456", "Alice", null, null, null);
                User bob = new User(null, "bob@example.com", "123456", "Bob", null, null, null);
                userRepo.save(alice);
                userRepo.save(bob);

                // 2. Tạo conversation PRIVATE
                Conversation convo = new Conversation();
                convo.setType(ConversationType.PRIVATE);
                convoRepo.save(convo);

                // 3. Gán Alice và Bob vào conversation
                memberRepo.save(new ConversationMember(null, alice, convo, null));
                memberRepo.save(new ConversationMember(null, bob, convo, null));
            }
        };
    }
}
