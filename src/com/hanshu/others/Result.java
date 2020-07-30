package com.hanshu.others;

import com.hanshu.bean.Comment;
import com.hanshu.bean.Image;
import com.hanshu.bean.User;

import java.util.List;


public class Result {
    private List<Image> imageList;
    private List<User> userList;
    private List<Comment> commentList;
    private int respondedPage;
    private int maxPage;

    public Result() {

    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public int getRespondedPage() {
        return respondedPage;
    }

    public void setRespondedPage(int respondedPage) {
        this.respondedPage = respondedPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
