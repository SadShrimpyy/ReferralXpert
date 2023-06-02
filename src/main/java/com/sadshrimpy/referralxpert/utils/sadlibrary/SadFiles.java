package com.sadshrimpy.referralxpert.utils.sadlibrary;

public class SadFiles {

    // All the files / directory
    protected final String configName = "config.yml";
    protected final String messagesName = "messages.yml";
    protected final String sqliteName = "refXsqlite.db";

    protected final String sqliteKey = "secret-key-sqlite.txt";
    protected final String sqliteKeyBackup = "secret-key-sqlite.backup";

    protected final String[] arrFiles = { this.configName, this.messagesName, this.sqliteName, this.sqliteKey };


    // Setter and Getters
    public String getConfigName() { return this.configName; }
    public String getMessagesName() { return this.messagesName; }
    public String getSqliteName() { return this.sqliteName; }
    public String getSqliteKey() {
        return this.sqliteKey;
    }
    public String getSqliteKeyBackup() {
        return this.sqliteKeyBackup;
    }
    public String[] getArrFiles() { return this.arrFiles; }
}
