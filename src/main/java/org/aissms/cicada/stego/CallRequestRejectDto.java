package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestRejectDto {
    String messagetype = "CALL_REQUEST_REJECTED";
    UserDto rejectedBy;
    
    public CallRequestRejectDto(UserDto rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public UserDto getRejectedBy() {
        return rejectedBy;
    }
}
