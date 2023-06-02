package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.ReferralXpert;
import org.bukkit.Bukkit;

import java.io.File;

public class SadGenerics extends SadChat {

    SadGenerics() {
        this.chat = new SadChat();
        this.plugin = (ReferralXpert) Bukkit.getServer().getPluginManager().getPlugin("ReferralXpert");
        assert plugin != null;
        this.pluginFolder = plugin.getDataFolder();
    }

    // Values
    private final SadChat chat;
    private final String version = "v1.0.0-Relase";
    private final ReferralXpert plugin;
    private final File pluginFolder;

    private void display() {
        chat.consoleHeader("&a  _____       __                    ___   __                _&r");
        chat.consoleHeader("&a |  __ \\     / _|                  | \\ \\ / /               | |&r");
        chat.consoleHeader("&a | |__) |___| |_ ___ _ __ _ __ __ _| |\\ V / _ __   ___ _ __| |_&r");
        chat.consoleHeader("&a |  _  // _ \\  _/ _ \\ '__| '__/ _` | | > < | '_ \\ / _ \\ '__| __|&r");
        chat.consoleHeader("&a | | \\ \\  __/ ||  __/ |  | | | (_| | |/ . \\| |_) |  __/ |  | |_&r");
        chat.consoleHeader("&a |_|  \\_\\___|_| \\___|_|  |_|  \\__,_|_/_/ \\_\\ .__/ \\___|_|   \\__|&r");
        chat.consoleHeader("&a                                           | |&r");
        chat.consoleHeader("&r -> Developed by: &aSadShrimpy#9190&r          &a|_|&r");
    }
    // Getters
    public String getVersion() { return this.version; }
    public ReferralXpert getPlugin() { return this.plugin; }
    public File getPluginFolder() { return this.pluginFolder; }

    // Alone boi
    public void displayHeader() { this.display(); chat.consoleHeader("&r -> Current version: &a" + this.version + "&r\n"); }
}