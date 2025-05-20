package com.example.repository;

import com.example.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {
}
