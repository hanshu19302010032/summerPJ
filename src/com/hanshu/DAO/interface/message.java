package com.hanshu.DAO.message;

import com.hanshu.bean.Message;

import java.util.List;

public interface message {

     void addSysMessage(Message sysMessage);

     List<Message> getMyMessage(int myUid);

     Message getMessage(int messageID);

     void deleteMessage(int messageID);
}
