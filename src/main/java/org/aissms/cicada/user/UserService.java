package org.aissms.cicada.user;

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
}
