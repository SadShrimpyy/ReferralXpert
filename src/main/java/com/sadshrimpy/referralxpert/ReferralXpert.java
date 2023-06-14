package com.sadshrimpy.referralxpert;

import com.sadshrimpy.referralxpert.commands.CommandManager;
import com.sadshrimpy.referralxpert.commands.TabCompleterManager;
import com.sadshrimpy.referralxpert.databases.sync.DBSyncType;
import com.sadshrimpy.referralxpert.databases.sync.DBSyncronization;
import com.sadshrimpy.referralxpert.events.PlayerJoinEv;
import com.sadshrimpy.referralxpert.events.PlayerQuitEv;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadLibrary;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ReferralXpert extends JavaPlugin {

    public static SadLibrary sadLibrary = new SadLibrary();

    @Override
    public void onEnable() {
        sadLibrary.initialize();

        CommandManager commandManager = new CommandManager();
        TabCompleterManager completerManager = new TabCompleterManager();
        PluginCommand baseCommand = getCommand("referralxpert");
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        registerCommands(commandManager, completerManager, baseCommand);
        registerEvents(pluginManager);

        DBSyncronization sync = new DBSyncronization();
        sync.registerTimer();
    }

    private void registerEvents(PluginManager pluginManager) {
        HashMap<Class<? extends Event>, EventExecutor> events = new HashMap<>(1);
        events.put(PlayerJoinEvent.class, new PlayerJoinEv().executor());
        events.put(PlayerQuitEvent.class, new PlayerQuitEv().executor());
        Listener listener = new Listener() {};

        events.forEach((ev, ex) -> pluginManager.registerEvent(ev, listener, EventPriority.MONITOR, ex, this, true));
    }

    private void registerCommands(CommandManager cM, TabCompleterManager tCM, PluginCommand genC) {
        genC.setExecutor(cM);
        genC.setTabCompleter(tCM);
    }

    @Override
    public void onDisable() {
        sadLibrary.destroy();
    }
}
