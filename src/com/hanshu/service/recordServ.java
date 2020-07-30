package com.hanshu.service;

import com.hanshu.bean.Record;
import com.hanshu.DAO.friendRecord.record;
import com.hanshu.DAO.friendRecord.recordDAO;

import java.sql.Connection;

public class recordServ {
    private Connection connection;

    public recordServ(Connection connection) {
        this.connection = connection;
    }

    void makeFriend(int uid1, int uid2) {
        try {
            record friendRecordDao = new recordDAO(connection);

            connection.setAutoCommit(false);

            boolean result1 = friendRecordDao.addFriendRecord(new Record(uid1, uid2));
            boolean result2 = friendRecordDao.addFriendRecord(new Record(uid2, uid1));

            boolean result = result1 & result2;

            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);
        } catch (Exception ignored) {
        }

    }

    public boolean deleteFriend(int uid1, int uid2) {
        try {
            record friendRecordDao = new recordDAO(connection);

            Record friendRecord1=friendRecordDao.getFriendRecord(uid1,uid2);

            if(friendRecord1==null){
                return false;
            }


            connection.setAutoCommit(false);

            boolean result1 = friendRecordDao.deleteFriendRecord(uid1, uid2);
            boolean result2 = friendRecordDao.deleteFriendRecord(uid2, uid1);

            if (result1 & result2) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);

            return result1 & result2;

        } catch (Exception e) {
            return false;
        }
    }


}
