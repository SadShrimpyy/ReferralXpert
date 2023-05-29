package com.sadshrimpy.referralxpert.databases.sqllite;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

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
        try {
            if (conn == null)
                conn = DriverManager.getConnection(new StringBuilder().append("jdbc:sqlite:").append(new File(sadLibrary.generics().getPluginFolder(), sadLibrary.files().getSQLiteName())).toString());
        } catch (SQLException e) {
//                throw new RuntimeException(e);
            return 0;
        }
        return 1;
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
