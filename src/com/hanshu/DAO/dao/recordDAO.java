package com.hanshu.DAO.friendRecord;

import com.hanshu.bean.Record;
import com.hanshu.DAO.GenericDao;

import java.sql.Connection;

public class recordDAO extends GenericDao<Record> implements record {
    private Connection connection;


    public recordDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addFriendRecord(Record friendRecord) {
        try {
            String sql = "insert into travelfriendrecord (UID1, UID2) VALUES (?,?);";
            int rowsAffected = this.update(connection, sql, friendRecord.getUID1(), friendRecord.getUID2());
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteFriendRecord(int uid1, int uid2) {
        try {
            String sql = "delete from travelfriendrecord where UID1=? and UID2=?";
            this.update(connection, sql, uid1, uid2);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Record getFriendRecord(int uid1, int uid2) {
        try {
            String sql = "select * from travelfriendrecord where UID1=? and UID2=?";
            return this.queryForOne(connection, sql, uid1, uid2);

        } catch (Exception e) {
            return null;
        }
    }
}
