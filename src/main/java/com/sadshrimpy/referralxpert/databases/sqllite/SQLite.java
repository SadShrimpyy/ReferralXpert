package com.sadshrimpy.referralxpert.databases.sqllite;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements DatabaseSyntax {

    private Connection conn;

    @Override
    public boolean create(String name) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:simple.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public byte connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:simple.db");
            } catch (SQLException e) {
//                throw new RuntimeException(e);
                return 0;
            }
            return 1;
        }
        return 2;
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
    public boolean query(String query) {
        return false;
    }
}
