package com.sadshrimpy.referralxpert.databases.procedures;

public enum DBType {
    SQLite("SQLite"),
    MySQL("MySQL");

    private String type;

    DBType(String type) {
        this.type = type;
    }
}
