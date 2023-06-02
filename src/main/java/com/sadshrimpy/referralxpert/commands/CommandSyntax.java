package com.sadshrimpy.referralxpert.commands;

import org.bukkit.command.CommandSender;

public interface CommandSyntax {
    abstract String getName();
    abstract String getPermission(String[] args);
    abstract boolean hasSubcommands();
    abstract int expectedArgs();
    abstract void perform(CommandSender sender);
}
