package org.aissms.cicada.stego;

import java.util.Hashtable;

import org.springframework.stereotype.Component;

@Component
class CallRepository {
    
    private static Hashtable<String, Call> callMap = new Hashtable<>();

    public void addCall(Call call) {
        callMap.put(call.getEmail1(), call);
        callMap.put(call.getEmail2(), call);
    }
    public Call getCall(String email) {
        return callMap.get(email);
    }
    public void remove(Call call) {
        if(call == null) return;
        callMap.remove(call.getEmail1());
        callMap.remove(call.getEmail2());
    }
}
