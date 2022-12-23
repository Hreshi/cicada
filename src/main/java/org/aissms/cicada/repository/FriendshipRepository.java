package org.aissms.cicada.repository;

import java.util.List;

import org.aissms.cicada.entity.Friendship;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends CrudRepository<Friendship, String>{
    
    List<Friendship> findByUsername(String username);
    List<Friendship> findByFriend(String friend);
    void deleteByUsernameAndFriend(String username, String friend);
}
