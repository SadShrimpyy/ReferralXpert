package com.sadshrimpy.referralxpert.databases.sqllite;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class SQLite implements DatabaseSyntax {

    private Connection conn;
    private Statement stmt;

    @Override
    public boolean connect(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
            if (conn == null)
                conn = DriverManager.getConnection(new StringBuilder().append("jdbc:sqlite:").append(new File(sadLibrary.generics().getPluginFolder(), name)).toString());
        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean close() {
        try {
            conn.close();
            return conn.isClosed();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            return false;
        }
    }

    @Override // TODO: 5/29/2023 function : tableExists
    public boolean tableExists(String table) {
        return false;
    }

    @Override // TODO: 5/29/2023 function : databaseExists
    public boolean databaseExists(String database) {
        return false;
    }

    @Override // TODO: 5/29/2023 function : dumpDatabase
    public boolean dumpDatabase(String path) {
        return false;
    }

    @Override // TODO: 5/29/2023 function : importDatabase
    public boolean importDatabase(String path) {
        return false;
    }

    @Override // TODO: 5/29/2023 function : query
    public boolean query(String query) {
        try {
            if (stmt == null) {
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            } else if (stmt.isClosed()) {
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            }
        } catch (SQLException e) {
//                throw new RuntimeException(e);
        }
        return false;
    }
}
