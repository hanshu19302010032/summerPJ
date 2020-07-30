package com.hanshu.bean;

public class CommentFavor {
    private int commentFavorID;
    private int commentID;
    private int uid;

    public CommentFavor(int commentID, int uid) {
        this.commentID = commentID;
        this.uid = uid;
    }


    public int getCommentID() {
        return commentID;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
