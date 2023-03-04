package org.aissms.cicada.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired UserRepository userRepository;

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) return null;
        return user.mapToUserDto();
    }
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
    public User findById(String id) {
        User user = userRepository.findById(id).get();
        return user;
    }
    public List<User> findByIdIn(List<String> ids) {
        return userRepository.findByIdIn(ids);
    }
    public void save(User user) {
        userRepository.save(user);
    }
}
