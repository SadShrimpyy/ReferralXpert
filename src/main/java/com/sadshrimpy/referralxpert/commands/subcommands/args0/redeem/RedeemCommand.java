package com.sadshrimpy.referralxpert.commands.subcommands.args0.redeem;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import static com.sadshrimpy.referralxpert.ReferralXpert.cache;
import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class RedeemCommand implements CommandSyntax {

    private String[] cmdArgs;
    private SadChat chat = sadLibrary.chat();
    private FileConfiguration msg = sadLibrary.configurations().getMessages();

    public RedeemCommand(String[] strings) {
        cmdArgs = strings;
    }

    @Override
    public String getName() {
        return "redeem";
    }

    @Override
    public String getPermission(String[] args) {
        return sadLibrary.permissions().getCreate();
    }

    @Override
    public boolean hasSubcommands() { return false; }

    @Override
    public int expectedArgs() {
        return 2;
    }

    @Override
    public int possibleErrors() {
        return 0;
    }

    @Override
    public void perform(CommandSender sender) {
        if (!cache.DB_allReferrals().contains(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.redeem.not-found")
                    .replace(sadLibrary.placeholders().getPlayerName(), sender.getName())
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return;
        }

        if (!cache.CACHE_referralsCreated().containsKey(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.redeem.not-found")
                    .replace(sadLibrary.placeholders().getPlayerName(), sender.getName())
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return;
        }

        // TODO: 14/06/2023 ip check
        // TODO: 14/06/2023 playtime check
        // TODO: 14/06/2023 redeem per player check
    }
}
