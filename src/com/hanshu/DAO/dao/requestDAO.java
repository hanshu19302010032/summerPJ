package com.hanshu.DAO.friendRequest;

import com.hanshu.DAO.GenericDao;

import java.sql.Connection;
import java.util.List;

public class requestDAO extends GenericDao<com.hanshu.bean.Request> implements request {
    private Connection connection;
    public requestDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean requestExists(int senderUID, int receiverUID) {
        try {
            String sql = "select * from friendrequest where senderUID=? and receiverUID=?";
            com.hanshu.bean.Request friendRequest
                    = this.queryForOne(connection, sql, senderUID, receiverUID);
            return !(friendRequest == null);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addRequest(com.hanshu.bean.Request friendRequest) {
        try {
            String sql = "insert into friendrequest (senderUID, receiverUID) VALUES (?,?)";
            int rowsAffected = this.update(connection, sql, friendRequest.getSenderUID(), friendRequest.getReceiverUID());
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<com.hanshu.bean.Request> getFriendRequestsIReceived(int myUid) {
        try {
            String sql = "select traveluser.UserName as senderUsername,senderUID,receiverUID,RequestID from friendrequest inner join traveluser on senderUID=traveluser.UID where receiverUID=?";
            return this.queryForList(connection, sql, myUid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public com.hanshu.bean.Request getFriendRequestByID(int requestID) {
        try {
            String sql = "select traveluser.UserName as senderUsername,senderUID,receiverUID,RequestID from friendrequest inner join traveluser on senderUID=traveluser.UID where RequestID=?";
            return this.queryForOne(connection, sql, requestID);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteFriendRequest(int requestID) {
        try {
            String sql = "delete from friendrequest where RequestID=?";
            int rowsAffected = this.update(connection, sql, requestID);
            return rowsAffected>0;
        } catch (Exception e) {
            return false;
        }
    }
}
