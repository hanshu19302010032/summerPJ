package com.hanshu.DAO.commentFavor;

import com.hanshu.bean.CommentFavor;
import com.hanshu.DAO.GenericDao;

import java.sql.Connection;
import java.util.List;

public class commentFavorDAO extends GenericDao<CommentFavor> implements commentFavor {
    private Connection connection;

    public commentFavorDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean favorExists(int uid, int commentID) {
        try {
            String sql = "select * from travemcommentfavor where uid=? and commentID=?";
            List<CommentFavor> commentFavorList = this.queryForList(connection, sql, uid, commentID);
            return commentFavorList.size() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addCommentFavor(CommentFavor commentFavor) {
        try {
            String sql = "insert into travemcommentfavor (commentID, uid) VALUES (?,?)";
            int rowsAffected = this.update(connection, sql, commentFavor.getCommentID(), commentFavor.getUid());
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CommentFavor getCommentFavor(int commentID) {
        try {
            String sql = "select * from travemcommentfavor where and commentID=?";
            return this.queryForOne(connection, sql, commentID);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteCommentFavor(int uid, int commentID) {
        try {
            String sql = "delete from travemcommentfavor where uid=? and commentID=?";
            int rowsAffected = this.update(connection, sql, uid, commentID);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
