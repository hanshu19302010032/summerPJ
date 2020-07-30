package com.hanshu.DAO.message;

import com.hanshu.bean.Message;
import com.hanshu.DAO.GenericDao;

import java.sql.Connection;
import java.util.List;

public class messageDAO extends GenericDao<Message> implements message {

    private Connection connection;

    public messageDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addSysMessage(Message sysMessage) {

    }

    @Override
    public List<Message> getMyMessage(int myUid) {
        try {
            String sql = "select * from sysmessage where receiverUID=?";
            return this.queryForList(connection, sql, myUid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Message getMessage(int messageID) {
        try {
            String sql = "select * from sysmessage where messageID=?";
            return this.queryForOne(connection, sql, messageID);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteMessage(int messageID) {
    }
}
