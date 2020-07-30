package com.hanshu.service;

import com.hanshu.bean.Message;
import com.hanshu.bean.User;
import com.hanshu.DAO.friendRequest.request;
import com.hanshu.DAO.friendRequest.requestDAO;
import com.hanshu.DAO.message.message;
import com.hanshu.DAO.message.messageDAO;
import com.hanshu.others.Action;

import java.sql.Connection;
import java.util.List;

public class requestServ {
    private Connection connection;

    public requestServ(Connection connection) {
        this.connection = connection;
    }



    public Action addRequest(com.hanshu.bean.Request friendRequest) {
        try {
            if (friendRequest.getSenderUID() == friendRequest.getReceiverUID()) {
                return new Action(false, "you can't befriend with yourself");
            }

            request friendRequestDao = new requestDAO(connection);

            if (friendRequestDao.requestExists(friendRequest.getSenderUID(), friendRequest.getReceiverUID())) {
                return new Action(false, "you have sent your request");
            }

            boolean success = friendRequestDao.addRequest(friendRequest);

            if (success) {
                return new Action(true, "you have sent your request");
            } else {
                return new Action(false, "request sent failed");
            }

        } catch (Exception e) {
            return new Action(false, "befriend failed");
        }


    }

    public List<com.hanshu.bean.Request> getFriendRequestsIReceived(int myUid) {
        try {
            request friendRequestDao = new requestDAO(connection);

            return friendRequestDao.getFriendRequestsIReceived(myUid);

        } catch (Exception e) {
            return null;
        }

    }

    public Action refuseRequest(User me, int requestID) {

        request friendRequestDao = new requestDAO(connection);

        com.hanshu.bean.Request friendRequest = friendRequestDao.getFriendRequestByID(requestID);

        if (friendRequest == null) return new Action(false, "did not receive");

        if (me.getUid() != friendRequest.getReceiverUID()) return new Action(false, "did not receive");

        boolean success = friendRequestDao.deleteFriendRequest(requestID);

        message sysMessageDao = new messageDAO(connection);


        if (success) {
            sysMessageDao.addSysMessage(new Message(
                    friendRequest.getSenderUID(), me.getUsername() + "refused you"
            ));

            return new Action(true, "refused" + friendRequest.getSenderUsername());
        } else {
            return new Action(false, "refuse failed");
        }

    }

    public Action acceptRequest(User me, int requestID) {
        try {
            request friendRequestDao = new requestDAO(connection);

            com.hanshu.bean.Request friendRequest = friendRequestDao.getFriendRequestByID(requestID);

            if (friendRequest == null) return new Action(false, "did not receive");

            if (friendRequest.getReceiverUID() != me.getUid()) return new Action(false, "did not receive");


            boolean resultOfDeleteMessage = friendRequestDao.deleteFriendRequest(requestID);


            if (resultOfDeleteMessage) {

                message sysMessageDao = new messageDAO(connection);

                recordServ recordServ = new recordServ(connection);

                sysMessageDao.addSysMessage(
                        new Message(friendRequest.getSenderUID(), me.getUsername() + "has accepted")
                );

                recordServ.makeFriend(friendRequest.getSenderUID(), me.getUid());

                return new Action(true,"accept succeeded");
            } else {
                return new Action(false, "accept failed");
            }

        } catch (Exception e) {
            return new Action(false, "accept failed");
        }
    }


}
