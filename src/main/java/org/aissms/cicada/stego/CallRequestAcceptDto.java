package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestAcceptDto {
    static String MESSAGE_TYPE = "CALL_REQUEST_ACCEPT";
    UserDto acceptedBy;
    
    public CallRequestAcceptDto(UserDto acceptedBy) {
        this.acceptedBy = acceptedBy;
    }
    public static String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }
    public UserDto getAcceptedBy() {
        return acceptedBy;
    }
}
