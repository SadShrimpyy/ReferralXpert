package com.sadshrimpy.referralxpert.databases.sync;

public enum DBSyncType {
    ASYNC("A-SYNC"),
    SYNC("SYNC");

    private String type;

    DBSyncType(String type) {
        this.type = type;
    }
}
