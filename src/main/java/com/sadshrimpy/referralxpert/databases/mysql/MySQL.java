package com.sadshrimpy.referralxpert.databases.mysql;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

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
    public boolean query(String query) {
        return false;
    }
}
