package org.aissms.cicada.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;

// user
public class User {
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String avatarUrl;
    
    private Set<String> inviteReceived;

    private Set<String> inviteSent;

    // key is user id and value is conversation id
    private Map<String, String> conversation;
    
    public User() {

    }
    public String getName() {
        return name;
    }
    public void setName(String firstName) {
        this.name = firstName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Set<String> getInviteReceived() {
        if(inviteReceived == null) this.inviteReceived = new HashSet<String>();
        return inviteReceived;
    }
    public void setInviteReceived(Set<String> inviteFrom) {
        this.inviteReceived = inviteFrom == null ? new HashSet<String>() : inviteFrom;
    }
    public Set<String> getInviteSent() {
        if(inviteSent == null) this.inviteSent = new HashSet<String>();
        return inviteSent;
    }
    public void setInviteSent(Set<String> inviteTo) {
        this.inviteSent = inviteTo == null ? new HashSet<String>() : inviteTo;
    }
    public Map<String,String> getConversation() {
        if(conversation == null) this.conversation = new HashMap<String, String>();
        return conversation;
    }
    public void setConversation(HashMap<String, String> conversation) {
        this.conversation = conversation == null ? new HashMap<String, String>() : conversation;
    }

    public static List<UserDto> mapToUserDto(List<User> userList) {
        List<UserDto> dtoList = new ArrayList<UserDto>();
        for(User user : userList) {
            dtoList.add(new UserDto(user));
        }
        return dtoList;
    }
}
