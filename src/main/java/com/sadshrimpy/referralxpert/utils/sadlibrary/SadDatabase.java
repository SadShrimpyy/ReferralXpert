package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.databases.DbProcedures;

import java.sql.Connection;

public class SadDatabase {
    public SadDatabase() {
    }

    public Connection getConnection() {
        Connection conn = new DbProcedures().AnalyzeType();
        if (conn == null) return null;

        return conn;
    }
}
