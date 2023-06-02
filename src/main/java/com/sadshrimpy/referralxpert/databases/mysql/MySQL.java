package com.sadshrimpy.referralxpert.databases.mysql;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

import java.sql.ResultSet;

public class MySQL implements DatabaseSyntax {

    @Override
    public boolean connect(String name) {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public boolean tableExists(String table) {
        return false;
    }

    @Override
    public boolean databaseExists(String database) {
        return false;
    }

    @Override
    public boolean dumpDatabase(String path) {
        return false;
    }

    @Override
    public boolean importDatabase(String path) {
        return false;
    }

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public boolean whosConnected() {
        return false;
    }

    @Override
    public ResultSet query(String query) {
        return null;
    }
}
