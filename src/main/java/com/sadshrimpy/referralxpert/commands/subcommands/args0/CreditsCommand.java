package com.sadshrimpy.referralxpert.commands.subcommands.args0;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import org.bukkit.command.CommandSender;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class CreditsCommand implements CommandSyntax {

    public CreditsCommand(String[] strings) {}

    @Override
    public String getName() { return "credits"; }

    @Override
    public String getPermission(String[] args) { return ""; }

    @Override
    public boolean hasSubcommands() { return false; }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void perform(CommandSender sender) {
        StringBuilder msg = new StringBuilder().append("\n&7&m&l              &7&l< &c&lReferral&6&lX&e&lpert &7&l>&m              ").append("&r")
                .append("\n&8 -> &eDeveloped &7with &c&l<3 &7by: &9&oSadShrimpy#9190").append("&r")
                .append("\n&8 -> &eVersion&7: &a").append(sadLibrary.generics().getVersion()).append("&r")
                .append("\n&8 -> &7Please write &2/referralxpert help &7for a list of commands").append("&r")
                .append("\n&7&m&l              &7&l< &c&lReferral&6&lX&e&lpert &7&l>&m              ");
        sender.sendMessage(sadLibrary.chat().viaChat(false, msg.toString()));
    }
}
