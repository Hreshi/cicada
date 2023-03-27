package org.aissms.cicada.stego;

import java.util.ArrayList;

class Call {
    // email1 is the caller
    String email1;
    String email2;
    boolean callStarted;
    boolean store;
    ArrayList<String> msgFromEmail1;
    ArrayList<String> msgFromEmail2;
    long ringEndTime;

    public Call(String user1, String user2, long ringEndTime) {
        this.email1 = user1;
        this.email2 = user2;
        this.ringEndTime = ringEndTime;
        this.store = true;
        msgFromEmail1 = new ArrayList<>();
        msgFromEmail2 = new ArrayList<>();
    }

    public String getEmail1() {
        return email1;
    }

    public String getEmail2() {
        return email2;
    }

    public boolean isCallStarted() {
        return callStarted;
    }

    public void setCallStarted(boolean callStarted) {
        this.callStarted = callStarted;
    }
    public void stopStoring() {
        store = false;
    }
    public boolean canStore() {
        return store;
    }
    public long getRingEndTime() {
        return ringEndTime;
    }
    public boolean ringExpired() {
        return System.currentTimeMillis() > ringEndTime;
    }
    public ArrayList<String> getMsgFromEmail1() {
        return msgFromEmail1;
    }
    public ArrayList<String> getMsgFromEmail2() {
        return msgFromEmail2;
    }
}