package com.hanshu.DAO.commentFavor;

import com.hanshu.bean.CommentFavor;

public interface commentFavor {

     boolean favorExists(int uid, int commentID);

     boolean addCommentFavor(CommentFavor commentFavor);

     CommentFavor getCommentFavor(int commentID);

     boolean deleteCommentFavor(int uid, int commentID);

}
