package org.aissms.cicada.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

// can find user by email and id
@Document("user")
public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);
    Optional<User> findById(String id);
    List<User> findByIdIn(List<String> id);
}
