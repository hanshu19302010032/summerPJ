package com.hanshu.DAO.comment;

import com.hanshu.bean.Comment;

import java.util.List;

public interface comment {

   boolean addComment(Comment newComment);

    List<Comment> getComments(int imageID, String howToOrder);

    Comment getComment(int commentID);
}
