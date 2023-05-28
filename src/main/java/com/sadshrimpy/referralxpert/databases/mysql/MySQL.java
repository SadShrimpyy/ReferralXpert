package com.sadshrimpy.referralxpert.databases.mysql;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

public class MySQL implements DatabaseSyntax {
    @Override
    public boolean create(String name) {
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
    public boolean query(String query) {
        return false;
    }
}
