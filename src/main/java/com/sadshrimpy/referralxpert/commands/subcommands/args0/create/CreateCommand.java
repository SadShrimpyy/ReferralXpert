package com.sadshrimpy.referralxpert.commands.subcommands.args0.create;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

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
        if (!sadLibrary.codes().getCodes().add(cmdArgs[1]))
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.cannot-create")
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
        else {
            SadPlaceholders place = sadLibrary.placeholders();
            double usages = Double.parseDouble(cmdArgs[2]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[2]);

            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.created")
                    .replace(place.getCode(), cmdArgs[1])
                    .replace(place.getCodeMaxUsages(), Integer.toString((int) usages))
                    .replace(place.getPlayerName(), cmdArgs[3])));

            // TODO: 6/8/2023 add the code to the list
        }
    }
}
