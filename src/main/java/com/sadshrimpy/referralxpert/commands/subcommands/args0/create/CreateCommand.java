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
        if (canBeCreated(sender)) return;

        Player involved = Bukkit.getPlayer(cmdArgs[4]);
        assert involved != null;

        SadPlaceholders place = sadLibrary.placeholders();
        double usa_usages = parseDoubleInfinity(2);
        double per_period = parseDoubleInfinity(3);

        Per_Result per_result = getPer_result(per_period);
        Usa_Result usa_result = getUsa_result(usa_usages);

        sendCreationMessage(sender, involved, place, per_result, usa_result);

        cache.CACHE_referralsCreated().put(cmdArgs[1], new Referral(cmdArgs[1], involved.getUniqueId(), (long) per_period, per_result.per_infinity, (long) usa_usages, usa_result.usa_once));
    }

    /** USAGES - Class & getter */
    private Usa_Result getUsa_result(double usa_usages) {
        MultiansType usa_once = MultiansType.NO;
        StringBuilder strCodeMaxUsages = new StringBuilder();
        if (usa_usages < 2147483647)
            strCodeMaxUsages.append((int) usa_usages);
        else {
            usa_once = MultiansType.YES;
            strCodeMaxUsages.append("Infinity");
        }
        Usa_Result usa_result = new Usa_Result(usa_once, strCodeMaxUsages);
        return usa_result;
    }

    private class Usa_Result {

        public final MultiansType usa_once;
        public final StringBuilder strCodeMaxUsages;
        public Usa_Result(MultiansType usa_once, StringBuilder strCodeMaxUsages) {
            this.usa_once = usa_once;
            this.strCodeMaxUsages = strCodeMaxUsages;
        }

    }
    /** PERIOD - Class & getter */
    private Per_Result getPer_result(double per_period) {
        MultiansType per_infinity = MultiansType.NO;
        StringBuilder strPeriod = new StringBuilder();
        if (per_period < 2147483647)
            strPeriod.append((int) per_period);
        else {
            per_infinity = MultiansType.YES;
            strPeriod.append("Infinity");
        }
        Per_Result per_result = new Per_Result(per_infinity, strPeriod);
        return per_result;
    }

    private class Per_Result {

        public final MultiansType per_infinity;
        public final StringBuilder strPeriod;
        public Per_Result(MultiansType per_infinity, StringBuilder strPeriod) {
            this.per_infinity = per_infinity;
            this.strPeriod = strPeriod;
        }

    }
    /** Parse the args with their value and the infinity */
    private double parseDoubleInfinity(int x) {
        return Double.parseDouble(cmdArgs[x]) <= 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(cmdArgs[x]);
    }

    /** Checks that needed to be false in order to create the referral */
    private boolean canBeCreated(CommandSender sender) {
        if (cache.DB_allReferrals().contains(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.cannot-create")
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return true;
        }

        if (cache.CACHE_referralsCreated().containsKey(cmdArgs[1])) {
            sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.cannot-create")
                    .replace(sadLibrary.placeholders().getCode(), cmdArgs[1])));
            return true;
        }

        if (cmdArgs.length == expectedArgs() - possibleErrors()) {
            cmdArgs = Arrays.copyOf(cmdArgs, cmdArgs.length + 1);
            cmdArgs[4] = sender.getName();
        }

        if (Bukkit.getPlayer(cmdArgs[4]) == null) {
            sender.sendMessage(chat.viaChat(true, msg.getString("player.not-found")
                    .replace(sadLibrary.placeholders().getPlayerName(), sender.getName())
                    .replace(sadLibrary.placeholders().getPlayerInvolved(), cmdArgs[4])));
            return true;
        }
        return false;
    }

    /** Send the creation message to the sender of the command */
    private void sendCreationMessage(CommandSender sender, Player involved, SadPlaceholders place, Per_Result per_result, Usa_Result usa_result) {
        sender.sendMessage(chat.viaChat(true, msg.getString("referral.creation.created")
                .replace(place.getCode(), cmdArgs[1])
                .replace(place.getCodeMaxUsages(), usa_result.strCodeMaxUsages)
                .replace(place.getPeriod(), per_result.strPeriod)
                .replace(place.getPlayerName(), involved.getName())));
    }
}
