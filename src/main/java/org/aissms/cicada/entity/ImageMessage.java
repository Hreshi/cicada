package org.aissms.cicada.entity;

public class ImageMessage extends ClientMessage{
    private String type;

    public ImageMessage(String sender, String recver, String content, String type) {
        super(sender, recver, content);
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
