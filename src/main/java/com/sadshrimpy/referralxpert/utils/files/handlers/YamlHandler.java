package com.sadshrimpy.referralxpert.utils.files.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


import java.io.File;
import java.io.IOException;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class YamlHandler implements FilesIn {
    private FileConfiguration configuration;
    private final File file;

    public YamlHandler(String fileName) {
        this.file = new File(sadLibrary.generics().getPluginFolder(), fileName);
        this.configuration = get(); // todo: add reload -> get another time
    }

    @Override
    public boolean initialize() throws Exception {
        boolean returned = true;
        if (!file.exists()) {
            if(file.createNewFile()) {
                sadLibrary.generics().getPlugin().saveResource(file.getName(), true);
                sadLibrary.messages().viaConsole(false, "&rThe file &f" + file.getName() + "&r: &rwas created &acorrectly &rusing defaults values.");
                configuration = get();
            }
            else {
                sadLibrary.messages().viaConsole(false, "&eThe file &f" + file.getName() + "&r: &ccannot &rbe created.");
                configuration = null;
                returned = false;
            }
        }
        return returned;
    }

    @Override
    public void save() {
        try {
            this.configuration = get();
            configuration.save(file.getName());
            //System.out.println(file.getName() + " saved");
        } catch (IOException e) {
            sadLibrary.messages().viaConsole(true, "&cError &rencountered while uploading the file &f" + file.getName() + "&r.");
        }
    }
    @Override
    public void reload() { configuration = YamlConfiguration.loadConfiguration(file); }

    @Override
    public boolean exixts() { return new File(sadLibrary.generics().getPlugin().getDataFolder(), file.getName()).exists(); }

    @Override
    public FileConfiguration get() { return YamlConfiguration.loadConfiguration(new File(sadLibrary.generics().getPluginFolder(), file.getName())); }
}