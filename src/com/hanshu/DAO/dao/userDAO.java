package com.hanshu.DAO.user;

import com.hanshu.bean.User;
import com.hanshu.DAO.GenericDao;
import com.hanshu.others.Action;
import com.hanshu.utils.MD5Utils;

import java.sql.Connection;
import java.util.List;

public class userDAO extends GenericDao<User> implements user {
    private Connection connection;


    public userDAO(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Action insertAUser(User user) {
        String sql = "insert into traveluser (Email, UserName, Pass, State, DateJoined, DateLastModified, salt, sessionID) VALUES (?,?,?,?,?,?,?,?);";
        try {
            if (userExists(user.getUsername())) return new Action(false, "the username already exists");
            int insertedRecords = this.update(connection, sql, user.getEmail(), user.getUsername(), user.getPass(), user.getState(), user.getDateJoined(), user.getDateLastModified(), user.getSalt(), user.getSessionID());
            if (insertedRecords == 0) return new Action(false, "register failed");
            return new Action(true, "register succeeded");
        } catch (Exception e) {
            return new Action(false, "register failed");
        }


    }

    @Override
    public Action tryLogin(String username, String password, String newSessionID) {
        try {
            String sql = "select * from traveluser where username=?";
            List<User> userList = this.queryForList(this.connection, sql, username);
            if (userList.size() == 0) return new Action(false, "wrong username or password");
            String salt = userList.get(0).getSalt();
            String encryptedPassword = MD5Utils.MD5(password + salt);

            sql = "select * from traveluser where username=? and pass=?";
            userList = this.queryForList(this.connection, sql, username, encryptedPassword);

            if (userList.size() == 1) {
                sql = "update traveluser set sessionID=? where UserName=?";
                this.update(connection, sql, newSessionID, username);
                return new Action(true, "login succeeded");
            }
            return new Action(false, "wrong username or password");

        } catch (Exception e) {
            return new Action(false, "login failed");
        }


    }


    @Override
    public User tryAutoLogin(String username, String sessionID) {
        try {

            String sql = "select * from traveluser where UserName=? and sessionID=?";
            return this.queryForOne(this.connection, sql, username, sessionID);
        } catch (Exception e) {
            return null;
        }

    }


    @Override
    public List<User> searchUserToAddFriend(String username) {
        try {
            String sql = "select UserName,DateJoined,Email from traveluser where UserName regexp ?";
            return this.queryForList(connection, sql, username);
        } catch (Exception e) {
            return null;
        }


    }



    public boolean userExists(String username) {
        try {
            return !(this.getUser(username) == null);
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public User getUser(String username) {
        try {
            String sql = "select * from traveluser where username=?";
            return this.queryForOne(this.connection, sql, username);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<User> getMyFriendList(int myuid) {
        try {
            String sql = "select UserName,UID,Email,DateJoined  from travelfriendrecord inner join traveluser on travelfriendrecord.UID2=traveluser.UID where UID1=?";
            return this.queryForList(connection, sql, myuid);


        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean setCanBeSeenFavor(int uid, int canBeSeenFavor) {
        try {
            String sql = "update traveluser set canBeSeenFavors=? where UID=?";
            this.update(connection, sql, canBeSeenFavor, uid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
