package org.aissms.cicada.repository;

import org.aissms.cicada.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    User findByUsername(String username);
}
