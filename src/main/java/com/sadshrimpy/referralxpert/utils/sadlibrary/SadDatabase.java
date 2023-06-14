package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.databases.DbProceduresT;

import java.sql.Connection;
import java.sql.SQLException;

public class SadDatabase {

    private Connection connection;
    private final DbProceduresT dbProceduresT;

    SadDatabase() {
        dbProceduresT = new DbProceduresT();
    }

    public void connect() {
        connection = dbProceduresT.getFilteredConnection();
        dbProceduresT.buildTables();
        try {
            connection.close();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }

    public void open() {
        try {
            if (connection.isClosed()) connection = dbProceduresT.getFilteredConnection();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }
}
