package com.hanshu.service;

import com.hanshu.bean.Message;
import com.hanshu.DAO.message.message;
import com.hanshu.DAO.message.messageDAO;

import java.sql.Connection;

public class messageServ {
    private Connection connection;

    public messageServ(Connection connection) {
        this.connection = connection;
    }

    public void deleteMessage(int myUid, int messageID) {
        message sysMessageDao = new messageDAO(connection);
        Message sysMessageToBeDeleted = sysMessageDao.getMessage(messageID);

        if (sysMessageToBeDeleted == null) return;
        if (sysMessageToBeDeleted.getReceiverUID() != myUid) return;

        sysMessageDao.deleteMessage(messageID);

    }
}
