package com.sadshrimpy.referralxpert.databases;

public enum DatabaseType {
    SQLite("SQLite"),
    MySQL("MySQL");

    private String value;

    DatabaseType(String value) {
        this.value = value;
    }

    public boolean isMySQL(DatabaseType type) {
        return type.equals(MySQL);
    }

    public boolean isSQLite(DatabaseType type) {
        return type.equals(SQLite);
    }
}
