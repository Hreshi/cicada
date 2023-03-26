package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestDto {
    String messagetype = "CALL_REQUEST";
    long ringEndTime;
    UserDto userDto;

    public CallRequestDto(long ringEndTime, UserDto userDto) {
        this.ringEndTime = ringEndTime;
        this.userDto = userDto;
    }
    public String getMessagetype() {
        return messagetype;
    }
    public long getRingEndTime() {
        return ringEndTime;
    }
    public UserDto getUserDto() {
        return userDto;
    }
    
}
