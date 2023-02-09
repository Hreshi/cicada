package org.aissms.cicada.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// can find user by email and id
@Repository
public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);
    Optional<User> findById(String id);
    List<User> findByIdIn(List<String> id);
}
