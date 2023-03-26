package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestAcceptDto {
    String messagetype = "CALL_REQUEST_ACCEPTED";
    UserDto acceptedBy;
    
    public CallRequestAcceptDto(UserDto acceptedBy) {
        this.acceptedBy = acceptedBy;
    }
    public String getMessagetype() {
        return messagetype;
    }
    public UserDto getAcceptedBy() {
        return acceptedBy;
    }
}
