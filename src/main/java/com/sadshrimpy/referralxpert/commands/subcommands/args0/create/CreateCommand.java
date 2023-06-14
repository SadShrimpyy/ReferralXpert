package com.sadshrimpy.referralxpert.commands.subcommands.args0.create;

import com.sadshrimpy.referralxpert.codes.Code;
import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class CreateCommand implements CommandSyntax {

    private String[] cmdArgs;
    private SadChat chat = sadLibrary.chat();
    private FileConfiguration msg = sadLibrary.configurations().getMessages();

    public CreateCommand(String[] strings) {
        cmdArgs = strings;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission(String[] args) {
        return sadLibrary.permissions().getCreate();
    }

    @Override
    public boolean hasSubcommands() { return false; }

    @Override
    public int expectedArgs() {
        return 5;
    }

    @Override
    public int possibleErrors() {
        return 1;
    }

    @Override
    public void perform(CommandSender sender) {
        if (sadLibrary.codes().getCodes().containsKey(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.cannot-create")
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return;
        }

        if (cmdArgs.length == expectedArgs() - possibleErrors()) {
            cmdArgs = Arrays.copyOf(cmdArgs, cmdArgs.length + 1);
            cmdArgs[4] = sender.getName();
        }

        if (Bukkit.getPlayer(cmdArgs[4]) == null) {
            sender.sendMessage(chat.viaChat(true, msg.getString("player.not-found")
                    .replace(sadLibrary.placeholders().getPlayerName(), sender.getName())
                    .replace(sadLibrary.placeholders().getPlayerInvolved(), cmdArgs[4])));
            return;
        }

        Player involved = Bukkit.getPlayer(cmdArgs[4]);
        assert involved != null;

        SadPlaceholders place = sadLibrary.placeholders();
        double usages = Double.parseDouble(cmdArgs[2]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[2]);
        double interval = Double.parseDouble(cmdArgs[3]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[3]);

        boolean once = false;
        StringBuilder res = new StringBuilder();
        if (interval < 2147483647)
            res.append((int) interval);
        else {
            once = true;
            res.append("Infinity");
        }

        sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.created")
                .replace(place.getCode(), cmdArgs[1])
                .replace(place.getCodeMaxUsages(), (int) usages >= 2147483647 ? "Infinity" : Integer.toString((int) usages))
                .replace(place.getPeriod(), res.toString())
                .replace(place.getPlayerName(), involved.getName())));

        sadLibrary.codes().getCodes().put(cmdArgs[1], new Code(cmdArgs[1], usages, involved.getUniqueId(), once, res.toString()));
    }
}
