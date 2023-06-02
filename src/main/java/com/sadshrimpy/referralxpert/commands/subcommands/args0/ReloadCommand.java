package com.sadshrimpy.referralxpert.commands.subcommands.args0;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import org.bukkit.command.CommandSender;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class ReloadCommand implements CommandSyntax {

    public ReloadCommand(String[] strings) {
    }

    @Override
    public String getName() { return "reload"; }

    @Override
    public String getPermission(String[] args) { return sadLibrary.permissions().getReload(); }

    @Override
    public boolean hasSubcommands() { return false; }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void perform(CommandSender sender) {
        sadLibrary.buildFiles();
        if (sadLibrary.configurations().reloadFiles())
            sender.sendMessage(sadLibrary.chat().viaChat(true, sadLibrary.configurations().getMessages().getString("reloaded.correctly")));
        else
            sender.sendMessage(sadLibrary.chat().viaChat(true, sadLibrary.configurations().getMessages().getString("reloaded.incorrectly")));

    }
}
