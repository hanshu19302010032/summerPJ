package com.hanshu.bean;

public class Message {
    private int messageID;
    private int receiverUID;
    private String messageContent;

    public Message(int receiverUID, String messageContent) {
        this.receiverUID = receiverUID;
        this.messageContent = messageContent;
    }

    public int getReceiverUID() {
        return receiverUID;
    }

    public String getMessageContent() {
        return messageContent;
    }

}
