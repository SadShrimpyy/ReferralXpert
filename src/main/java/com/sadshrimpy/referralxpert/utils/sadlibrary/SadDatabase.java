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
        connection = dbProceduresT.analyzeType();
//        try {
//            if (connection.isClosed()) connection = connection.getMetaData().getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Connection refresh() {
        try {
            if (connection.isClosed()) connection = dbProceduresT.analyzeType();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return connection;
    }
}
