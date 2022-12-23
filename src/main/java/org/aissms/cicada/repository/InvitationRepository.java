package org.aissms.cicada.repository;

import java.util.List;

import org.aissms.cicada.entity.Invitation;
import org.springframework.data.repository.CrudRepository;

public interface InvitationRepository extends CrudRepository<Invitation, String>{
    
    List<Invitation> findByInviteFrom(String username);
    List<Invitation> findByInviteTo(String username);
    Invitation findByInviteFromAndInviteTo(String inviteFrom, String inviteTo);
    void deleteByInviteFromAndInviteTo(String inviteFrom, String inviteTo);
}
