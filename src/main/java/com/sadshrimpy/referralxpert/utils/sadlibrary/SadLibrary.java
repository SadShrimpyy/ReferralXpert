package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.utils.files.FilesBuilder;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SadLibrary {

    private SadConfigurations configurations;
    private SadPlaceholders placeholders;
    private SadPermissions permissions;
    private SadDatabase connection;
    private SadGenerics generics;
    private SadChat chat;
    private SadFiles files;
    private SadDate date;

    private HashMap<UUID, Date> onlineTime;

    public void initialize() {
        // Instance all the classes
        this.generics = new SadGenerics();
        this.placeholders = new SadPlaceholders();
        this.permissions = new SadPermissions();
        this.date = new SadDate();
        this.chat = new SadChat();
        this.files = new SadFiles();
        this.configurations = new SadConfigurations();
        this.connection = new SadDatabase();

        this.generics.displayHeader();
        this.buildFiles();

        this.connection.getConnection();
//        this.buildDefaultJson();

        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
    }


    public SadConfigurations configurations() { return this.configurations; }
    public SadPlaceholders placeholders() { return this.placeholders; }
    public SadPermissions permissions() { return this.permissions; }
    public SadGenerics generics() { return this.generics; }
    public SadChat chat() { return this.chat; }
    public SadFiles files() { return this.files; }
    public SadDate date() { return this.date; }
    public SadDatabase database() { return this.connection; }


    public void buildFiles() {
        new FilesBuilder();
    }


    public HashMap<UUID, Date> getOnlineMap() {
        return onlineTime;
    }

    public void destroy() {
        this.generics = null;
        this.placeholders = null;
        this.permissions = null;
        this.date = null;
        this.chat = null;
        this.files = null;
        this.configurations = null;
        this.connection = null;

        this.onlineTime = null;
    }


    // Build the JSON
    /*
    private void buildDefaultJson() {
        try {
            this.configurations().getCurrentConfiguration().insert(new CurrentGenerics(MotdType.PERMANENT, this.date().getTime()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TEST
        try {
            sadLibrary.configurations().getCurrentConfiguration().insert(new CurrentGenerics(MotdType.TEMPORARY, sadLibrary.date().getTime()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<CurrentGenerics> db = sadLibrary.configurations().getHandler();
        System.out.println(db.get(0).getType());
        System.out.println(db.get(0).getLastUpdate());
    }
     */

}