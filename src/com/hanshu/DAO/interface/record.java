package com.hanshu.DAO.friendRecord;

import com.hanshu.bean.Record;

public interface record {

    boolean addFriendRecord(Record friendRecord);

    boolean deleteFriendRecord(int uid1, int uid2);

    Record getFriendRecord(int uid1, int uid2);
}
