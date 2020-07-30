package com.hanshu.bean;

public class Request {
    private int requestID;
    private int senderUID;
    private int receiverUID;
    private String senderUsername;

    public String getSenderUsername() {
        return senderUsername;
    }

    public Request(int senderUID, int receiverUID) {
        this.senderUID = senderUID;
        this.receiverUID = receiverUID;
    }

    public int getSenderUID() {
        return senderUID;
    }

    public int getReceiverUID() {
        return receiverUID;
    }

}
