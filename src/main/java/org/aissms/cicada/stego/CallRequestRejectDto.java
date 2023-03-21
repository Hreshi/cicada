package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestRejectDto {
    static String MESSAGE_TYPE = "CALL_REQUEST_REJECTED";
    UserDto rejectedBy;
    
    public CallRequestRejectDto(UserDto rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public UserDto getRejectedBy() {
        return rejectedBy;
    }
}
