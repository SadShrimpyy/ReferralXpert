package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class HelpCommand implements CommandSyntax {

    private List<String> cmdsInHelp;
    private String[] cmdArgs;
    private byte finish;
    private byte start;
    private byte size;
    private byte pageMax;
    private byte page;

    protected FileConfiguration msgC = sadLibrary.configurations().getMessages();
    protected SadPlaceholders place = sadLibrary.placeholders();


    public HelpCommand(String[] strings) {
        cmdArgs = strings;
    }

    public HelpCommand() {

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission(String[] args) {
        return sadLibrary.permissions().getHelp();
    }

    @Override
    public boolean hasSubcommands() {
        return false;
    }

    @Override
    public int expectedArgs() {
        return 2;
    }

    @Override
    public int possibleErrors() {
        return 1;
    }

    @Override
    public void perform(CommandSender sender) {
        cmdsInHelp = msgC.getStringList("help.list");
        SadChat sMsg = sadLibrary.chat();

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
        pageMax = (byte) ((cmdsInHelp.size() % size) == 0 ? cmdsInHelp.size() / size : cmdsInHelp.size() / size + 1);
        page = (byte) Math.max(Byte.parseByte(cmdArgs[1]), 1);
        if (page > pageMax) page = pageMax;

        finish = (byte) ((page * size - 1) > cmdsInHelp.size() ? cmdsInHelp.size() - 1 : (page * size - 1));
        start = (byte) (page < 1 ? 1 : (page - 1) * size);

        sendBanner(sender, this.msgC, sMsg);
        if (this.msgC.getBoolean("help.space-between-rows"))
            printWithSpace(sender, sMsg);
        else
            printWithoutSpace(sender, sMsg);
        sendBanner(sender, this.msgC, sMsg);

    }

    /**
     * Print the menu without the space
     * */
    private void printWithSpace(CommandSender sender, SadChat msgC) {
/*
        int remainCmds = cmds.size() - (size * (page - 1));
        if (remainCmds < size) // if remain cmds ...
            finish -= size - remainCmds; // NO 7
        if (finish > cmds.size()) finish = cmds.size();
*/
        RowPosition row = getRow(sender, msgC);
        if (row == null) return;

        boolean hPage = this.msgC.getBoolean("help.page.enabled");
        TextComponent rowBtns = new ButtonsRow(page, pageMax).getRowHoverable("help.page.row");
        Player player = Bukkit.getPlayer(sender.getName());

        if (hPage && row.isFirst(row))
            if (player != null) {
                player.spigot().sendMessage(rowBtns);
                sender.sendMessage("");
            }

        while (start <= finish && cmdsInHelp.size() > start) {
            sender.sendMessage(msgC.viaChat(false, cmdsInHelp.get(start)));
            if (start < finish)
                sender.sendMessage("");
            start++;
        }

        if (hPage && !row.isFirst(row))
            if (player != null) {
                sender.sendMessage("");
                player.spigot().sendMessage(rowBtns);
            }
    }

    /**
     * Print the menu without the space
     * */
    private void printWithoutSpace(CommandSender sender, SadChat msgC) {
        RowPosition row;

        row = getRow(sender, msgC);
        if (row == null) return;

        boolean hPage = this.msgC.getBoolean("help.page.enabled");
        TextComponent rowBtns = new ButtonsRow(page, pageMax).getRowHoverable("help.page.row");
        Player player = Bukkit.getPlayer(sender.getName());

        if (hPage && row.isFirst(row))
            if (player != null) {
                player.spigot().sendMessage(rowBtns);
                sender.sendMessage("");
            }

        while (start <= finish && cmdsInHelp.size() > start) {
            sender.sendMessage(msgC.viaChat(false, cmdsInHelp.get(start)));
            start++;
        }

        if (hPage && !row.isFirst(row))
            if (player != null) {
                sender.sendMessage("");
                player.spigot().sendMessage(rowBtns);
            }
    }

    /**
     * Get the row
     * */
    private RowPosition getRow(CommandSender sender, SadChat msgC) {
        RowPosition row;
        try {
            row = RowPosition.valueOf(this.msgC.getString("help.page.display"));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(msgC.viaChat(true, this.msgC.getString("plugin-error.row-not-valid")));
            return null;
        }
        return row;
    }

    /**
     * Send the banner
     * */
    private void sendBanner(CommandSender sender, FileConfiguration msg, SadChat msgC) {
        sender.sendMessage(msgC.viaChat(false, msg.getString("help.banner")
                .replace(place.getHelpCurPage(), Integer.toString(page))
                .replace(place.getHelpMaxPage(), Integer.toString(pageMax))));
    }
}
