package com.sadshrimpy.referralxpert.databases;

import java.sql.ResultSet;

public interface DatabaseSyntax {
    abstract boolean connect(String name);
    abstract boolean close();
    abstract boolean tableExists(String table);
    abstract boolean databaseExists(String database);
    abstract boolean dumpDatabase(String path);
    abstract boolean importDatabase(String path);
    abstract boolean checkConnection();
    abstract boolean whosConnected();
    abstract ResultSet query(String query);
}
