package org.aissms.cicada.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    private String username;
    private String friend;

    public Friendship() {
    }

    public Friendship(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
