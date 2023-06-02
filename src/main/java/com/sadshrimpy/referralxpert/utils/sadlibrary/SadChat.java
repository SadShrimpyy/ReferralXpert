package com.sadshrimpy.referralxpert.utils.sadlibrary;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class SadChat {
    private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    private final String prefixErr = "&4ERROR &e-> &r";
    private final String defaultPrefix = "&7[&cReferral&6X&epert&7] &8> &7";
    public String translateColors(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public String viaChat(boolean prefix, String message) {
        return prefix ? translateColors(sadLibrary.configurations().getMessages().getString("prefix") + message) : translateColors(message);
    }
    public void viaConsole(boolean isError, String message) {
        console.sendMessage(translateColors(defaultPrefix + (isError ? prefixErr : "") + message));
    }
    public void consoleHeader(String message) {
        console.sendMessage(translateColors(message));
    }
}
