package com.sadshrimpy.referralxpert.commands;

import com.sadshrimpy.referralxpert.commands.subcommands.args0.*;
import com.sadshrimpy.referralxpert.commands.subcommands.args0.create.CreateCommand;
import com.sadshrimpy.referralxpert.commands.subcommands.args0.help.HelpCommand;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class CommandManager implements CommandExecutor {

    private final Map<String, Function<String[], CommandSyntax>> subCommandsMap = new HashMap<>();

    public CommandManager() {
        subCommandsMap.put("credits", CreditsCommand::new);
        subCommandsMap.put("help", HelpCommand::new);
        subCommandsMap.put("create", CreateCommand::new);
        subCommandsMap.put("reload", ReloadCommand::new);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            subCommandsMap.get("credits").apply(args).perform(sender);
            return true;
        }

        String subCommandName = args[0].toLowerCase();

        SadPlaceholders placeholders = sadLibrary.placeholders();

        if (!subCommandsMap.containsKey(subCommandName)) {
            // The command is not present
            sender.sendMessage(sadLibrary.messages().viaChat(true, sadLibrary.configurations().getMessages().getString("command.not-found")
                    .replace(placeholders.getPlayerName(), sender.getName())
                    .replace(placeholders.getCommand(), commandPlaceholderBuilder(label, args))));
            //subCommandsMap.get("credits").apply(args).perform(sender);
            return true;
        }

        CommandSyntax subCommand = subCommandsMap.get(subCommandName).apply(args);
        String permission = subCommand.getPermission(args);

        /** Check permission(s) */
        if (permission == null) {
            sender.sendMessage(sadLibrary.messages().viaChat(true, sadLibrary.configurations().getMessages().getString("command.not-found")
                    .replace(placeholders.getPlayerName(), sender.getName())
                    .replace(placeholders.getCommand(), commandPlaceholderBuilder(label, args))));
            return true;
        }

        if (!sender.hasPermission(permission)) {
            sender.sendMessage(sadLibrary.messages().viaChat(true, sadLibrary.configurations().getMessages().getString("player.no-permission")
                    .replace(placeholders.getPlayerName(), sender.getName())
                    .replace(placeholders.getCommand(), commandPlaceholderBuilder(label, args))
                    .replace(placeholders.getPermission(), permission)));
            return true;
        }

        /** Check args length is valid */
        if (args.length != subCommand.expectedArgs()) {
            sender.sendMessage(sadLibrary.messages().viaChat(true, sadLibrary.configurations().getMessages().getString("command.not-complete")
                    .replace(placeholders.getPlayerName(), sender.getName())
                    .replace(placeholders.getCommand(), commandPlaceholderBuilder(label, args))));
            return true;
        }

        subCommand.perform(sender);
        return true;
    }

    private String commandPlaceholderBuilder(String label, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/").append(label).append(" ");
        for (String arg : args)
            if (arg != null)
                stringBuilder.append(arg).append(" ");
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
