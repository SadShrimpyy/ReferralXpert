package com.sadshrimpy.referralxpert.databases;

public interface DatabaseSyntax {
    abstract boolean create(String name);
    abstract boolean tableExists(String table);
    abstract boolean databaseExists(String database);
    abstract boolean query(String query);
}
