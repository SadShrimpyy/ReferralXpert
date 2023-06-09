package com.sadshrimpy.referralxpert.databases.procedures.types;

public enum DBType {
    SQLite("SQLite"),
    MySQL("MySQL");

    private String type;

    DBType(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}
