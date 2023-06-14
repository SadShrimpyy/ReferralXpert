package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.databases.DbProceduresT;

import java.sql.Connection;
import java.sql.SQLException;

public class SadDatabase {

    private Connection connection;
    private DbProceduresT dbProceduresT;

    SadDatabase() {
        dbProceduresT = new DbProceduresT();
    }

    public void connect() {
        connection = dbProceduresT.getFilteredConnection();
        dbProceduresT.buildTables();
    }

    public Connection refresh() {
        try {
            if (connection.isClosed()) connection = dbProceduresT.getFilteredConnection();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return connection;
    }
}
