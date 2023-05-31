package com.sadshrimpy.referralxpert.databases;

public interface DatabaseSyntax {
    abstract boolean connect(String name);
    abstract boolean close();
    abstract boolean tableExists(String table);
    abstract boolean databaseExists(String database);
    abstract boolean dumpDatabase(String path);

    // TODO: 5/29/2023 function : dumpDatabase
    boolean importDatabase(String path);

    abstract boolean query(String query);
}
