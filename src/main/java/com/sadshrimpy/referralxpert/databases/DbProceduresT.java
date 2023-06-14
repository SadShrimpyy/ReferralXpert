package com.sadshrimpy.referralxpert.databases;

import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class DbProceduresT {

    private FileConfiguration messages;
    private FileConfiguration config;
    private Connection connection;
    private SadChat chat;

    public DbProceduresT() {
        this.chat = sadLibrary.chat();
        this.messages = sadLibrary.configurations().getMessages();
        this.config = sadLibrary.configurations().getConfig();
    }

    public Connection getFilteredConnection() {
        if (!config.getBoolean("database.enabled")) {
            chat.viaConsole(true, messages.getString("plugin-error.no-database-detected"));
            return null;
        }

        String dbType = config.getString("database.type");
        if (DatabaseType.SQLite.name().equals(dbType))
            return getSqliteConnection();
        else if (DatabaseType.MySQL.name().equals(dbType))
            return getMysqlConnection();
        else {
            sadLibrary.chat().viaConsole(true, messages.getString("plugin-error.database-not-recognised"));
            return null;
        }
    }

    /** MySQL stuff */
    private Connection getMysqlConnection() {
        String DB_NAME = config.getString("database.MySQL.database-name");
        String DB_HOSTNAME = config.getString("database.MySQL.hostname");
        boolean DB_SSL = config.getBoolean("database.MySQL.use-ssl");
        int DB_PORT = config.getInt("database.MySQL.port");

        String DB_USERNAME = config.getString("database.MySQL.username");
        String DB_PASSWORD = config.getString("database.MySQL.password");
        
        String url = new StringBuilder().append("jdbc:mysql://").append(DB_HOSTNAME).append(":").append(DB_PORT).append("/").append("?useSSL=").append(DB_SSL).toString();
        String dbUrl = new StringBuilder().append("jdbc:mysql://").append(DB_HOSTNAME).append(":").append(DB_PORT).append("/").append(DB_NAME).append("?useSSL=").append(DB_SSL).toString();

        try {
            connection = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
            String query = new StringBuilder(50).append("SHOW DATABASES LIKE \"").append(DB_NAME).append("\";").toString();

            ResultSet set = connection.createStatement().executeQuery(query);

            if (!set.next()) {
                connection.createStatement().execute(new StringBuilder(45).append("CREATE DATABASE ").append(DB_NAME).append(";").toString());
                set = connection.createStatement().executeQuery(query);
                if (set.next()) connection = DriverManager.getConnection(dbUrl, DB_USERNAME, DB_PASSWORD);
            } else
                connection = DriverManager.getConnection(dbUrl, DB_USERNAME, DB_PASSWORD);

        } catch (SQLException e) {
            chat.viaConsole(true, messages.getString("plugin-error.could-not-connect-db")
                    .replace("%db-exec%", e.getCause().getMessage())
                    .replace("%db-type%", "MySQL"));
        }
        return connection;
    }

    /** SQLite stuff */
    private Connection getSqliteConnection() {
        try {
            StringBuilder secret = new StringBuilder().append(getSecretKey());
            if (secret == null) {
                secret.setLength(0);
                secret.append(generateKey());
            }
            else if (secret.length() == 0 ) {
                secret.setLength(0);
                secret.append(generateKey());
            } else {
                System.out.println("len: " + secret.length());
            }
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(String.valueOf(new StringBuilder().append("jdbc:sqlite:").append(new File(sadLibrary.generics().getPluginFolder(), sadLibrary.files().getSqliteName()))));
        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
            chat.viaConsole(true, messages.getString("plugin-error.could-not-connect-db")
                    .replace("%db-exec%", e.getCause().getMessage())
                    .replace("%db-type%", "SQLite"));
        }
        return null;
    }

    private char[] generateKey() {
        return new char[0];
    }

    protected String getSecretKey() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sadLibrary.files().getSqliteKey()));
            String line = bufferedReader.readLine();
            bufferedReader.close();
            return line;
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }

    /** BOTH stuff (Query etc.) */
    public void buildTables() {
        String[] query = new String[5];
        query[0] = (new StringBuilder(180)
                .append("CREATE TABLE IF NOT EXISTS player (")
                .append("IdPlaPk INT(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                .append("streak INT(5) NOT NULL,")
                .append("uuid VARCHAR(37) NOT NULL,")
                .append("last_code VARCHAR(101) NOT NULL")
                .append(");").toString());

        query[1] = (new StringBuilder(160)
                .append("CREATE TABLE IF NOT EXISTS period (")
                .append("IdPerPk INT(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                .append("period INT(9) NOT NULL,")
                .append("infinity ENUM(\"yes\",\"no\") NOT NULL")
                .append(");").toString());

        query[2] = (new StringBuilder(150)
                .append("CREATE TABLE IF NOT EXISTS usages (")
                .append("IdUsaPk INT(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                .append("usages INT(10) NOT NULL,")
                .append("infinity ENUM(\"yes\",\"no\") NOT NULL")
                .append(");").toString());

        query[3] = (new StringBuilder(330)
                .append("CREATE TABLE IF NOT EXISTS referral (")
                .append("IdRefPk INT(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                .append("IdPerFk INT(9) NOT NULL,")
                .append("IdUsaFk INT(9) NOT NULL,")
                .append("code VARCHAR(101) NOT NULL,")
                .append("owner_uuid VARCHAR(37) NOT NULL,")
                .append("once ENUM(\"yes\",\"no\") NOT NULL,")
                .append("FOREIGN KEY(IdPerFk) REFERENCES period(IdPerPk),")
                .append("FOREIGN KEY(IdUsaFk) REFERENCES usages(IdUsaPk)")
                .append(");").toString());

        query[4] = (new StringBuilder(190)
                .append("CREATE TABLE IF NOT EXISTS claim (")
                .append("IdPlaFk INT(9) NOT NULL,")
                .append("IdRefFk INT(9) NOT NULL,")
                .append("FOREIGN KEY(IdPlaFk) REFERENCES player(IdPlaPk),")
                .append("FOREIGN KEY(IdRefFk) REFERENCES referral(IdRefPk)")
                .append(");").toString());

        try {
            Statement stmt = connection.createStatement();
            for (int i = 0; i < query.length; i++)
                stmt.executeUpdate(query[i]);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }
}
