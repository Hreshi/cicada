package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;

public class CallRequestDto {
    static String MESSAGE_TYPE = "CALL_REQUEST";
    long ringEndTime;
    UserDto userDto;

    public CallRequestDto(long ringEndTime, UserDto userDto) {
        this.ringEndTime = ringEndTime;
        this.userDto = userDto;
    }
    public long getRingEndTime() {
        return ringEndTime;
    }
    public UserDto getUserDto() {
        return userDto;
    }
    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }
}
