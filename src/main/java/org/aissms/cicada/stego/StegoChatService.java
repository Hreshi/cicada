package org.aissms.cicada.stego;

import org.aissms.cicada.user.UserDto;
import org.aissms.cicada.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class StegoChatService {
    static Integer RING_SPAN_SECONDS = 20;

    @Autowired
    CallRepository callRepository;
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired UserService userService;

    public boolean busy(String email) {
        Call call = callRepository.getCall(email);
        if(call == null) return false;
        if(call.callStarted) return true;
        if(call.ringExpired()) {
            callRepository.remove(call);
            return false;
        }
        return true;
    }

    // email1 has called email2, email2 is being rung
    // returns ring end time in ms
    public long ringUser(String email1, String email2) {
        long ringEndTime = calculateRingEndTime();
        
        Call call = new Call(email1, email2, ringEndTime);
        callRepository.addCall(call);

        
        CallRequestDto chatRequestDto = new CallRequestDto(ringEndTime, userService.getUserByEmail(email1));
        messagingTemplate.convertAndSend("/messages/"+email2, chatRequestDto);
        return ringEndTime;
    }
    private long calculateRingEndTime() {
        long current = System.currentTimeMillis();
        current += RING_SPAN_SECONDS*1000;
        return current;
    }
    public void rejectCall(String rejectBy) {
        Call call = callRepository.getCall(rejectBy);
        callRepository.remove(call);
        if(call != null && !call.ringExpired()) {
            CallRequestRejectDto dto = new CallRequestRejectDto(userService.getUserByEmail(rejectBy));
            messagingTemplate.convertAndSend("/messages/"+call.getEmail1(), dto);
        }
    }
    public UserDto acceptCall(String email) {
        Call call = callRepository.getCall(email);
        if(call == null || call.ringExpired()) return null;
        String caller = call.getEmail1();
        UserDto callerDto = userService.getUserByEmail(caller);
        UserDto calleeDto = userService.getUserByEmail(call.getEmail2());

        messagingTemplate.convertAndSend("/messages/"+caller, new CallRequestAcceptDto(calleeDto));
        // mark call has started
        call.setCallStarted(true);
        return callerDto;
    }
    public void endCall(String email) {
        Call call = callRepository.getCall(email);
        if(call == null) return;
        String secondUser = call.getEmail1();
        if(secondUser.equals(email)) {
            secondUser = call.getEmail2();
        }
        callRepository.remove(call);
        messagingTemplate.convertAndSend("/messages/"+secondUser, new EndOfCallDto());
    }
}
