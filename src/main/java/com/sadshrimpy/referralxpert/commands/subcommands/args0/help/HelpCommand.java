package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadMessages;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
        cmdsInHelp = msgC.getStringList("help.list");

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

        // Take only bounded values
        finish = (byte) ((page * size - 1) > cmdsInHelp.size() ? cmdsInHelp.size() - 1 : (page * size - 1));
        start = (byte) (page < 1 ? 1 : (page - 1) * size);

        SadMessages msgC = sadLibrary.messages();

        // Send the help page
        sendBanner(sender, this.msgC, msgC);
        if (this.msgC.getBoolean("help.space-between-rows"))
            printWithSpace(sender, msgC);
        else
            printWithoutSpace(sender, msgC);
        sendBanner(sender, this.msgC, msgC);

    }

    private void printWithSpace(CommandSender sender, SadMessages msgC) {
/*
        int remainCmds = cmds.size() - (size * (page - 1));
        if (remainCmds < size) // if remain cmds ...
            finish -= size - remainCmds; // NO 7
        if (finish > cmds.size()) finish = cmds.size();
*/
        RowPosition row;

        try {
            row = RowPosition.valueOf(this.msgC.getString("help.page.display"));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(msgC.viaChat(true, this.msgC.getString("plugin-error.row-not-valid")));
            return;
        }

        boolean hPage = this.msgC.getBoolean("help.page.enabled");
        TextComponent rowBtns = new ButtonsRow(page, pageMax).getRowHoverable("help.page.row");
        Player player = Bukkit.getPlayer(sender.getName());

        if (hPage && row.isFirst(row)) {
            player.spigot().sendMessage(rowBtns);
            sender.sendMessage(msgC.viaChat(false, ""));
        }

        while (start <= finish && cmdsInHelp.size() > start) {
            sender.sendMessage(msgC.viaChat(false, cmdsInHelp.get(start)));
            if (start < finish)
                sender.sendMessage(msgC.viaChat(false, ""));
            start++;
        }

        if (hPage && !row.isFirst(row)) {
            sender.sendMessage(msgC.viaChat(false, ""));
            player.spigot().sendMessage(rowBtns);
        }
    }

    private void printWithoutSpace(CommandSender sender, SadMessages msgC) {
        // TODO: 5/26/2023 insert the row
        while ((start <= finish) && (cmdsInHelp.size() > start)) {
            sender.sendMessage(msgC.viaChat(false, cmdsInHelp.get(start)));
            start++;
        }
    }

    private void sendBanner(CommandSender sender, FileConfiguration msg, SadMessages msgC) {
        sender.sendMessage(msgC.viaChat(false, msg.getString("help.banner")
                .replace(place.getHelpCurPage(), Integer.toString(page))
                .replace(place.getHelpMaxPage(), Integer.toString(pageMax))));
    }
}
