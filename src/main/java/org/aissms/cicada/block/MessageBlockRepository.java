package org.aissms.cicada.block;

import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document("messageblock")
public interface MessageBlockRepository extends MongoRepository<MessageBlock, String>{
    Optional<MessageBlock> findById(String id);
}
