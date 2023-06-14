package com.sadshrimpy.referralxpert.commands;

import com.google.common.io.ByteSource;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
//import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class TabCompleterManager implements TabCompleter {

    private FileConfiguration msg = sadLibrary.configurations().getMessages();
    private CommandSender sender;
    private String[] args;

    @Override
    public LinkedList<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        this.sender = sender;
        this.args = args;

        boolean permission = sender.hasPermission(sadLibrary.permissions().getCompleter());

        switch (args.length) {
            case 1:
                return firstArg(permission);

            case 2:
                return secondArg(permission);

            case 3:
                return thirdArg(permission);

            case 4:
                return fourthArg(permission);

            case 5:
                return fivethArg(permission);
        }

        return new LinkedList<>();
    }


    private LinkedList<String> firstArg(boolean permission) {
        if (!permission)
            return StringUtil.copyPartialMatches(args[0], Collections.singletonList("<You don't have the permission>"), new LinkedList<String>());
        else
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "create", "check", "goals", "score", "scoreboard", "scoreboard-gui", "set", "maximum", "redeem", "reload", "reset", "time", "secret"), new LinkedList<>());
    }

    private LinkedList<String> secondArg(boolean permission) {
        if (!permission)
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("<You don't have the permission>"), new LinkedList<String>());

        switch (args[0].toLowerCase()) {
            case "help":
                LinkedList<String> secArgs = new LinkedList<>();
                int size = 3;
                int sz = msg.getStringList("<period>").size();
                int pageMax = (byte) ((sz % size) == 0 ? sz / size : sz / size + 1);
                for (int i = 1; i <= pageMax; i++)
                    secArgs.add(String.valueOf(i));
                return StringUtil.copyPartialMatches(args[1], secArgs, new LinkedList<String>());

            case "create":
                return StringUtil.copyPartialMatches(args[1], Collections.singleton("<Enter your code here>"), new LinkedList<String>());
        }
        return null;
    }


    private LinkedList<String> thirdArg(boolean permission) {
        if (!permission)
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("<You don't have the permission>"), new LinkedList<String>());

        switch (args[0].toLowerCase()) {
            case "create":
                return StringUtil.copyPartialMatches(args[2], Collections.singleton("<Enter the number of usages here>"), new LinkedList<String>());
        }
        return null;
    }

    private LinkedList<String> fourthArg(boolean permission) {
        if (!permission)
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("<You don't have the permission>"), new LinkedList<String>());

        switch (args[0].toLowerCase()) {
            case "create":
                return StringUtil.copyPartialMatches(args[3], Collections.singletonList("<Enter the hourly time interval here>"), new LinkedList<String>());

        }
        return null;
    }

    private LinkedList<String> fivethArg(boolean permission) {
        if (!permission)
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("<You don't have the permission>"), new LinkedList<String>());

        switch (args[0].toLowerCase()) {
            case "create":
                LinkedList<String> players = new LinkedList<>();
                for (Player player : Bukkit.getOnlinePlayers())
                    players.add(player.getName());
                return StringUtil.copyPartialMatches(args[4], players, new LinkedList<String>());

        }
        return null;
    }
}