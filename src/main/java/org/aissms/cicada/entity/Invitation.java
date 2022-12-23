package org.aissms.cicada.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    private String inviteFrom;
    private String inviteTo;
    
    public Invitation() {
    }

    public Invitation(String inviteFrom, String inviteTo) {
        this.inviteFrom = inviteFrom;
        this.inviteTo = inviteTo;
    }

    public String getInviteFrom() {
        return inviteFrom;
    }

    public void setInviteFrom(String inviteFrom) {
        this.inviteFrom = inviteFrom;
    }

    public String getInviteTo() {
        return inviteTo;
    }

    public void setInviteTo(String inviteTo) {
        this.inviteTo = inviteTo;
    }
    
}
