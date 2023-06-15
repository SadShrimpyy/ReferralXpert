package com.sadshrimpy.referralxpert.commands.subcommands.args0.create;

import com.sadshrimpy.referralxpert.referral.MultiansType;
import com.sadshrimpy.referralxpert.referral.Referral;
import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static com.sadshrimpy.referralxpert.ReferralXpert.cache;
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
        if (cache.getReferrals().containsKey(cmdArgs[1])) {
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
        double usa_usages = Double.parseDouble(cmdArgs[2]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[2]); // <global-usages>
        double per_period = Double.parseDouble(cmdArgs[3]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[3]); // <period>

        MultiansType per_infinity = MultiansType.NO;
        StringBuilder strPeriod = new StringBuilder();
        if (per_period < 2147483647)
            strPeriod.append((int) per_period);
        else {
            per_infinity = MultiansType.YES;
            strPeriod.append("Infinity");
        }

        MultiansType usa_once = MultiansType.NO;
        StringBuilder strCodeMaxUsages = new StringBuilder();
        if (usa_usages < 2147483647)
            strCodeMaxUsages.append((int) usa_usages);
        else {
            usa_once = MultiansType.YES;
            strCodeMaxUsages.append("Infinity");
        }

        sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.created")
                .replace(place.getCode(), cmdArgs[1])
                .replace(place.getCodeMaxUsages(), strCodeMaxUsages)
                .replace(place.getPeriod(), strPeriod)
                .replace(place.getPlayerName(), involved.getName())));

        cache.codeCreated().put(cmdArgs[1], new Referral(cmdArgs[1], involved.getUniqueId(), (long) per_period, per_infinity, (long) usa_usages, usa_once));
    }
}
