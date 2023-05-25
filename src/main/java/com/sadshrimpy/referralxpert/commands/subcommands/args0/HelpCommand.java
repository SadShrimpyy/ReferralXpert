package com.sadshrimpy.referralxpert.commands.subcommands.args0;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadConfigurations;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadMessages;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class HelpCommand implements CommandSyntax {

    private List<String> cmds;
    private int size;
    private int page;
    private int pageMax;
    private int finish;
    private int start;

    private String[] cmdArgs;

    public HelpCommand(String[] strings) {
        cmdArgs = strings;
    }

    @Override
    public String getName() { return "help"; }

    @Override
    public String getPermission(String[] args) { return sadLibrary.permissions().getHelp(); }

    @Override
    public boolean hasSubcommands() { return false; }

    @Override
    public int expectedArgs() {
        return 2;
    }

    @Override
    public void perform(CommandSender sender) {
        FileConfiguration msg = sadLibrary.configurations().getMessages();
        cmds = msg.getStringList("help.list");

/*
  // todo select the amount displayed
    # The size option select how many commands, per page will be displayed
    # You can print minimum 3 commands per page!
  size: 4
        // Minimum value bu default: 3 commands per page
        size = msg.getInt("help.size");
        if (size < 3) size = 3; // Positive and minimum values
*/
        size = 3;

        pageMax = (cmds.size() % size) == 0 ? cmds.size() / size : cmds.size() / size + 1;
        page = Math.max(Integer.parseInt(cmdArgs[1]), 1); // Take only positive && non-zero values
        if (page > pageMax) page = pageMax; // Limit the page value

        // Take only bounded values
        finish = (page * size - 1) > cmds.size() ? cmds.size() - 1 : (page * size - 1);
        start = page == 0 ? 0 : (page - 1) * size;

        SadMessages msgC = sadLibrary.messages();

        // Send the help page
        sendBanner(sender, msg, msgC);
        if (msg.getBoolean("help.space"))
            printWithSpace(sender, msgC);
        else
            printWithoutSpace(sender, msgC);
        sendBanner(sender, msg, msgC);

    }

    private void printWithSpace(CommandSender sender, SadMessages msgC) {
/*
        int remainCmds = cmds.size() - (size * (page - 1));
        if (remainCmds < size) // if remain cmds ...
            finish -= size - remainCmds; // NO 7
        if (finish > cmds.size()) finish = cmds.size();
*/

        while ((start <= finish) && (cmds.size() > start)) {
            sender.sendMessage(msgC.viaChat(false, cmds.get(start)));
            if ((start < finish)/* || (start < cmds.size())*/) sender.sendMessage(msgC.viaChat(false, ""));
            start++;
        }
    }

    private void printWithoutSpace(CommandSender sender, SadMessages msgC) {
        while ((start <= finish) && (cmds.size() > start)) {
            sender.sendMessage(msgC.viaChat(false, cmds.get(start)));
            start++;
        }
    }

    private void sendBanner(CommandSender sender, FileConfiguration msg, SadMessages msgC) {
        sender.sendMessage(msgC.viaChat(false, msg.getString("help.banner")
                .replace(sadLibrary.placeholders().getHelpCurPage(), Integer.toString(page))
                .replace(sadLibrary.placeholders().getHelpMaxPage(), Integer.toString(pageMax))));
    }
}
