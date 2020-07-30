package com.hanshu.DAO.user;

import com.hanshu.bean.User;
import com.hanshu.others.Action;

import java.util.List;


public interface user {


     Action insertAUser(User user);

     Action tryLogin(String username, String password, String newSessionID);

     User tryAutoLogin(String username, String sessionID);

     List<User> searchUserToAddFriend(String username);

     boolean userExists(String username);

     User getUser(String username);

     List<User> getMyFriendList(int myuid);

     boolean setCanBeSeenFavor(int uid,int canBeSeenFavor);

}

