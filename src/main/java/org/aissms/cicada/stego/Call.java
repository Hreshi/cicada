package org.aissms.cicada.stego;

class Call {
    // email1 is the caller
    String email1;
    String email2;
    boolean callStarted;
    long ringEndTime;

    public Call(String user1, String user2, long ringEndTime) {
        this.email1 = user1;
        this.email2 = user2;
        this.ringEndTime = ringEndTime;
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

    public long getRingEndTime() {
        return ringEndTime;
    }
    public boolean ringExpired() {
        return System.currentTimeMillis() > ringEndTime;
    }
}