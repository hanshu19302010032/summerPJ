package com.hanshu.others;

import java.util.ArrayList;

public class Record {
    private ArrayList<Browse> records = new ArrayList<>();
    private static final int maxNumOfRecords = 10;

    public Record() {

    }

    public void addRecord(Browse singleBrowseRecord) {
        int size = this.records.size();

        int newImageID = singleBrowseRecord.getImageID();


        for (int i = 0; i <= records.size() - 1; i++) {
            if (records.get(i).getImageID() == newImageID) {
                return;
            }
        }

        if (size < maxNumOfRecords) {
            records.add(singleBrowseRecord);
        } else {
            for (int i = 0; i <= maxNumOfRecords - 2; i++) {
                records.set(i, records.get(i + 1));
            }
            records.set(maxNumOfRecords - 1, singleBrowseRecord);
        }

    }

}
