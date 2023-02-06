package org.aissms.cicada.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// need mongodb here
public interface UserRepository extends JpaRepository<User, String>{
    User findByEmail(String email);
    List<User> findAll();
}
