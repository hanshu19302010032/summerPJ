package com.hanshu.bean;


public class Record {
    private int friendRecordID;
    private int UID1;
    private int UID2;

    public Record(int UID1, int UID2) {
        this.UID1 = UID1;
        this.UID2 = UID2;
    }

    public int getUID1() {
        return UID1;
    }


    public int getUID2() {
        return UID2;
    }
}
