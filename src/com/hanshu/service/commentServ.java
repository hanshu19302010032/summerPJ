package com.hanshu.service;

import com.hanshu.bean.Comment;
import com.hanshu.bean.Image;
import com.hanshu.bean.User;
import com.hanshu.DAO.comment.comment;
import com.hanshu.DAO.comment.commentDAO;
import com.hanshu.DAO.image.image;
import com.hanshu.DAO.image.imageDAO;
import com.hanshu.others.Action;
import com.hanshu.others.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class commentServ {

    private Connection connection;

    public commentServ(Connection connection) {
        this.connection = connection;
    }

    public Action addComment(Comment newComment) {
        try {
            comment commentDao = new commentDAO(connection);
            image imageDao = new imageDAO(connection);

            Image image = imageDao.getImage(newComment.getImageID());

            if (image == null) return new Action(false, "the photo does not exist！");

            boolean success = commentDao.addComment(newComment);

            if (success) {
                return new Action(true, "comment succeeded");
            } else {
                return new Action(false, "comment failed");
            }

        } catch (Exception e) {
            return new Action(false, "comment failed");
        }

    }

    public Result getComments(int imageID, User user, int requestedPage, int pageSize, String howToOrder, HttpServletRequest request) {
        try {
            comment commentDao = new commentDAO(connection);
            List<Comment> commentList = commentDao.getComments(imageID, howToOrder);

            return getSearchResult(commentList, requestedPage, pageSize, user,request);

        } catch (Exception e) {
            return null;
        }

    }

    private Result getSearchResult(List<Comment> originalCommentList, int requestedPage, int pageSize, User user, HttpServletRequest request) {
        ArrayList<Comment> subCommentList = new ArrayList<>();
        Result searchResult = new Result();


        int maxPage = (int) Math.ceil((double) originalCommentList.size() / pageSize);
        searchResult.setMaxPage(maxPage);

        requestedPage = Math.max(requestedPage, 1);
        requestedPage = Math.min(maxPage, requestedPage);

        searchResult.setRespondedPage(requestedPage);


        int start = pageSize * (requestedPage - 1);
        int end = Math.min(start + pageSize, originalCommentList.size());

        for (int i = start; i < end; i++) {
            subCommentList.add(originalCommentList.get(i));
        }



        searchResult.setCommentList(subCommentList);

        return searchResult;

    }


}
