package com.sadshrimpy.referralxpert;

import com.sadshrimpy.referralxpert.commands.CommandManager;
import com.sadshrimpy.referralxpert.commands.TabCompleterManager;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadLibrary;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReferralXpert extends JavaPlugin {

    public static SadLibrary sadLibrary = new SadLibrary();

    @Override
    public void onEnable() {
        // Plugin startup logic
        sadLibrary.initialize();
        CommandManager cmdManager = new CommandManager();
        TabCompleterManager tabCmpManager = new TabCompleterManager();

        // Cmds
        PluginCommand genericCmd = getCommand("referralxpert");
        genericCmd.setExecutor(cmdManager);
        genericCmd.setTabCompleter(tabCmpManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sadLibrary.destroy();
    }
}
