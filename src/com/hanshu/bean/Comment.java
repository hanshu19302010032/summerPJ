package com.hanshu.bean;

import java.sql.Timestamp;


public class Comment {
    private int commentID;
    private int imageID;
    private int uid;
    private String text;
    private Timestamp dateReleased;
    private String commenterName;
    private int favorCount;


    public Comment(int imageID, int uid, String text, Timestamp dateReleased) {
        this.imageID = imageID;
        this.uid = uid;
        this.text = text;
        this.dateReleased = dateReleased;
    }

    public int getCommentID() {
        return commentID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateReleased() {
        return dateReleased;
    }


}
