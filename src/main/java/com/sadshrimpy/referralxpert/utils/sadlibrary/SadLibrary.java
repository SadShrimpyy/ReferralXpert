package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.utils.files.FilesBuilder;

public class SadLibrary {

    private SadConfigurations configurations;
    private SadPlaceholders placeholders;
    private SadPermissions permissions;
    private SadDatabase connection;
    private SadGenerics generics;
    private SadReferrals codes;
    private SadFiles files;
    private SadChat chat;
    private SadDate date;


    public void initialize() {
        // Instance all the classes
        this.generics = new SadGenerics();
        this.placeholders = new SadPlaceholders();
        this.permissions = new SadPermissions();
        this.codes = new SadReferrals();
        this.date = new SadDate();
        this.chat = new SadChat();
        this.files = new SadFiles();
        this.configurations = new SadConfigurations();

        this.generics.displayHeader();
        this.buildFiles();

        this.connection = new SadDatabase();
        this.connection.connect();

//        this.buildDefaultJson();

    }


    public SadConfigurations configurations() { return this.configurations; }
    public SadPlaceholders placeholders() { return this.placeholders; }
    public SadPermissions permissions() { return this.permissions; }
    public SadDatabase database() { return this.connection; }
    public SadGenerics generics() { return this.generics; }
    public SadFiles files() { return this.files; }
    public SadChat chat() { return this.chat; }
    public SadDate date() { return this.date; }
    public SadReferrals codes() { return this.codes; }


    public void buildFiles() {
        new FilesBuilder();
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
        this.codes = null;
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