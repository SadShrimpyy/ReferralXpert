package com.sadshrimpy.referralxpert.databases;

public enum DatabaseType {
    SQLite("SQLite"),
    MySQL("MySQL");

    private String type;

    DatabaseType(String type) {
        this.type = type;
    }
}
