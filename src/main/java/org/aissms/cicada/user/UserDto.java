package org.aissms.cicada.user;

import org.aissms.cicada.mongo.FileController;

public class UserDto {
    public String email;
    public String name;
    public String avatarUrl;

    public UserDto() {
        
    }
    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = FileController.REQUEST_PATH + user.getAvatarUrl();
    }
}
