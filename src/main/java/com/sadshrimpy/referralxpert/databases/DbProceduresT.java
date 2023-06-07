package com.sadshrimpy.referralxpert.databases;

import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import org.bukkit.configuration.file.FileConfiguration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class DbProceduresT {

    private FileConfiguration config;
    private FileConfiguration messages;
    private SadChat chat;

    public DbProceduresT() {
        this.chat = sadLibrary.chat();
        this.messages = sadLibrary.configurations().getMessages();
        this.config = sadLibrary.configurations().getConfig();
    }

    public Connection analyzeType() {
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
        try {
            String url = new StringBuilder().append("jdbc:mysql://").append(config.getString("database.MySQL.hostname")).append(":").append(config.getInt("database.MySQL.port")).append("/").append(config.getString("database.MySQL.database-name")).toString();
            return DriverManager.getConnection(url, config.getString("database.MySQL.username"), config.getString("database.MySQL.password"));
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            chat.viaConsole(true, messages.getString("plugin-error.could-not-connect-db")
                    .replace("%db-exec%", e.getCause().getMessage())
                    .replace("%db-type%", "MySQL"));
        }
        return null;
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
}
