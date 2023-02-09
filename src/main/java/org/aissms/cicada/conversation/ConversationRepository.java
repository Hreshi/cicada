package org.aissms.cicada.conversation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document("conversation")
public interface ConversationRepository extends MongoRepository<Conversation, String>{
    Optional<Conversation> findById(String id);
    List<Conversation> findByIdIn(List<String> id);
}
