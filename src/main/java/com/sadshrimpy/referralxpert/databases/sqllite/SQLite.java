package com.sadshrimpy.referralxpert.databases.sqllite;

import com.sadshrimpy.referralxpert.databases.DatabaseSyntax;

import java.io.File;
import java.sql.*;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class SQLite implements DatabaseSyntax {

    private Connection conn;
    private Statement stmt;
    private ResultSet resultSet;

    @Override
    public boolean connect(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
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
    public boolean dumpDatabase(String outPath) {
        try {
            String dumpCommand = "SCRIPT TO '" + outPath + "'";
            Statement statement = conn.createStatement();
            statement.execute(dumpCommand);
            statement.close();
            sadLibrary.messages().viaConsole(false, sadLibrary.configurations().getConfig().getString("---"));
            System.out.println("Dump del database SQLite creato con successo.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override // TODO: 5/29/2023 function : importDatabase
    public boolean importDatabase(String path) {
        return false;
    }

    @Override
    public boolean checkConnection() {
        try {
            if (conn != null && !conn.isClosed())
                return true;
            else
                return false;
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean whosConnected() {
        return false;
    }

    public ResultSet query(String query) {
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
            resultSet.close();
            stmt.close();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
