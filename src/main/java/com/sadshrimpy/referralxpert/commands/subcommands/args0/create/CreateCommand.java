package com.sadshrimpy.referralxpert.commands.subcommands.args0.create;

import com.sadshrimpy.referralxpert.codes.Codes;
import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

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
        return 4;
    }

    @Override
    public void perform(CommandSender sender) {
//        /rxp create <code> <maximum-usages> <player>
        if (sadLibrary.codes().getCodes().containsKey(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.cannot-create")
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return;
        }

        if (Bukkit.getPlayer(cmdArgs[3]) == null) {
            sender.sendMessage(chat.viaChat(true, msg.getString("player.not-found")
                    .replace(sadLibrary.placeholders().getPlayerName(), sender.getName())
                    .replace(sadLibrary.placeholders().getPlayerInvolved(), cmdArgs[3])));
            return;
        }

        Player involved = Bukkit.getPlayer(cmdArgs[3]);
        assert involved != null;

        SadPlaceholders place = sadLibrary.placeholders();
        double usages = Double.parseDouble(cmdArgs[2]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[2]);

        sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.created")
                .replace(place.getCode(), cmdArgs[1])
                .replace(place.getCodeMaxUsages(), Integer.toString((int) usages))
                .replace(place.getPlayerName(), cmdArgs[3])));

        sadLibrary.codes().getCodes().put(cmdArgs[1], new Codes(cmdArgs[1], usages, involved.getUniqueId()));
    }
}
