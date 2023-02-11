package org.aissms.cicada.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// ignore password while reading
@Document("user")
public class User {
    @JsonIgnore
    private String id;
    private String email;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String avatarUrl;
    
    @JsonIgnore
    private Set<String> inviteFrom;

    @JsonIgnore
    private Set<String> inviteTo;

    // key is user id and value is conversation id
    @JsonIgnore
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
    public Set<String> getInviteFrom() {
        if(inviteFrom == null) this.inviteFrom = new HashSet<String>();
        return inviteFrom;
    }
    public void setInviteFrom(Set<String> inviteFrom) {
        this.inviteFrom = inviteFrom == null ? new HashSet<String>() : inviteFrom;
    }
    public Set<String> getInviteTo() {
        if(inviteTo == null) this.inviteTo = new HashSet<String>();
        return inviteTo;
    }
    public void setInviteTo(Set<String> inviteTo) {
        this.inviteTo = inviteTo == null ? new HashSet<String>() : inviteTo;
    }
    public Map<String,String> getConversation() {
        if(conversation == null) this.conversation = new HashMap<String, String>();
        return conversation;
    }
    public void setConversation(HashMap<String, String> conversation) {
        this.conversation = conversation == null ? new HashMap<String, String>() : conversation;
    }
}
