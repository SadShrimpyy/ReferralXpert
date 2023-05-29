package com.sadshrimpy.referralxpert.utils.sadlibrary;

public class SadFiles {

    // All the files / directory
    protected final String configName = "config.yml";
    protected final String messagesName = "messages.yml";
    protected final String sqliteName = "refXsqlite.db";

    protected final String[] arrFiles = { this.configName, this.messagesName, this.sqliteName };


    // Setter and Getters
    public String getConfigName() { return this.configName; }
    public String getMessagesName() { return this.messagesName; }
    public String getSQLiteName() { return this.sqliteName; }

    public String[] getArrFiles() { return this.arrFiles; }
}
