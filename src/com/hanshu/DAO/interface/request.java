package com.hanshu.DAO.friendRequest;

import com.hanshu.bean.Request;

import java.util.List;

public interface request {
     boolean requestExists(int senderUID, int receiverUID);

     boolean addRequest(com.hanshu.bean.Request friendRequest);

     List<com.hanshu.bean.Request> getFriendRequestsIReceived(int myUid);

     com.hanshu.bean.Request getFriendRequestByID(int requestID);

     boolean deleteFriendRequest(int requestID);
}
