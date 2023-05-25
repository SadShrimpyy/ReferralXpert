package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.utils.files.FilesBuilder;

public class SadLibrary {

    public void initialize() {
        // Instance all the classes
        this.generics = new SadGenerics();
        this.placeholders = new SadPlaceholders();
        this.permissions = new SadPermissions();
        this.date = new SadDate();
        this.messages = new SadMessages();
        this.files = new SadFiles();
        this.configurations = new SadConfigurations();


        // Trickys SHotoSH
        generics.displayHeader();
        this.buildFiles();
        //this.buildDefaultJson();
    }

    // Generics related
    public SadGenerics generics() { return this.generics; }
    protected SadGenerics generics;

    // Placeholders related
    protected SadPlaceholders placeholders;
    public SadPlaceholders placeholders() { return this.placeholders; }

    // Permissions related
    protected SadPermissions permissions;
    public SadPermissions permissions() { return this.permissions; }

    // Date related
    protected SadDate date;
    public SadDate date() { return this.date; }

    // Messages related
    protected SadMessages messages;
    public SadMessages messages() { return this.messages; }

    // Files related
    protected SadFiles files;
    public SadFiles files() { return this.files; }

    // Configurations related
    protected SadConfigurations configurations;
    public SadConfigurations configurations() { return this.configurations; }

    // Build all the files
    public void buildFiles() {
        new FilesBuilder();
    }

    public void destroy() {
        this.generics = null;
        this.placeholders = null;
        this.permissions = null;
        this.date = null;
        this.messages = null;
        this.files = null;
        this.configurations = null;
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