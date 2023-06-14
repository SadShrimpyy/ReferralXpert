package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadChat;
import com.sadshrimpy.referralxpert.utils.sadlibrary.SadPlaceholders;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class HelpCommand implements CommandSyntax {

    private List<String> helpList;
    private String[] cmdArgs;
    private byte finish;
    private byte start;
    private byte size;
    private byte pageMax;
    private byte page;

    private Map<String, HoverEvent> heMap = new HashMap<>();

    protected FileConfiguration msg = sadLibrary.configurations().getMessages();
    protected SadChat chat = sadLibrary.chat();
    protected SadPlaceholders place = sadLibrary.placeholders();


    public HelpCommand(String[] strings) {
        cmdArgs = strings;
        initHeMap();
    }

    public HelpCommand() {
        initHeMap();
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
        helpList = msg.getStringList("help.list");
        int sz = helpList.size();

/*
  // todo select the amount displayed
    # The size option select how many commands, per page will be displayed
    # You can print minimum 3 commands per page!
  size: 4
        // Minimum value bu default: 3 commands per page
        size = msg.getInt("help.size");
        if (size < 3) size = 3; // Positive and minimum values
*/

        if (cmdArgs.length == expectedArgs() - possibleErrors()) {
            cmdArgs = Arrays.copyOf(cmdArgs, cmdArgs.length + 1);
            cmdArgs[1] = "1";
        } else {
            try {
                Byte.parseByte(cmdArgs[1]);
            } catch (NumberFormatException e) {
                cmdArgs[1] = "127";
            }
        }

        size = 3;
        pageMax = (byte) ((sz % size) == 0 ? sz / size : sz / size + 1);
        page = (byte) Math.max(Byte.parseByte(cmdArgs[1]), 1);
        if (page > pageMax) page = pageMax;

        finish = (byte) ((page * size - 1) > sz ? sz - 1 : (page * size - 1));
        start = (byte) (page < 1 ? 1 : (page - 1) * size);

        sendBanner(sender, this.msg, chat);
        if (this.msg.getBoolean("help.space-between-rows"))
            printWithSpace(sender, chat);
        else
            printWithoutSpace(sender, chat);
        sendBanner(sender, this.msg, chat);

    }

    /**
     * Print the menu without the space
     * */
    private void printWithSpace(CommandSender sender, SadChat chat) {
/*
        int remainCmds = cmds.size() - (size * (page - 1));
        if (remainCmds < size) // if remain cmds ...
            finish -= size - remainCmds; // NO 7
        if (finish > cmds.size()) finish = cmds.size();
*/
        RowPosition row = getRow(sender, chat);
        if (row == null) return;

        boolean hPage = this.msg.getBoolean("help.page.enabled");
        TextComponent rowBtns = new ButtonsRow(page, pageMax).getRowHoverable("help.page.row");
        Player player = Bukkit.getPlayer(sender.getName());

        // TODO: 15/06/2023 fix console command sender
        Player.Spigot spigot = Objects.requireNonNull(player).spigot();

        if (hPage && row.isFirst(row))
            if (player != null) {
                spigot.sendMessage(rowBtns);
                sender.sendMessage("");
            }

        TextComponent tc = new TextComponent();
        while (start <= finish && helpList.size() > start) {
//            sender.sendMessage(chat.viaChat(false, helpList.get(start)));
            // TODO: 14/06/2023 create text component and send
            tc.addExtra(chat.translateColors(helpList.get(start)));
            tc.setHoverEvent(getHoverable(helpList.get(start)));
            spigot.sendMessage(tc);
            tc.setText("");
            tc.setHoverEvent(null);
            tc.setExtra(new ArrayList<>(0));
            if (start < finish)
                sender.sendMessage("");
            start++;
        }

        if (hPage && !row.isFirst(row))
            if (player != null) {
                sender.sendMessage("");
                spigot.sendMessage(rowBtns);
            }
    }

    /**
     * Return the translated hoverable text for the specific command
     * */
    private HoverEvent getHoverable(String query) {
        for (String command : heMap.keySet())
            if (query.contains(command))
                return heMap.get(command);
        return null;
    }

    /**
     * Print the menu without the space
     * */
    private void printWithoutSpace(CommandSender sender, SadChat msgC) {
        RowPosition row;

        row = getRow(sender, msgC);
        if (row == null) return;

        boolean hPage = this.msg.getBoolean("help.page.enabled");
        TextComponent rowBtns = new ButtonsRow(page, pageMax).getRowHoverable("help.page.row");
        Player player = Bukkit.getPlayer(sender.getName());

        if (hPage && row.isFirst(row))
            if (player != null) {
                player.spigot().sendMessage(rowBtns);
                sender.sendMessage("");
            }

        while (start <= finish && helpList.size() > start) {
            sender.sendMessage(msgC.viaChat(false, helpList.get(start)));
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
            row = RowPosition.valueOf(this.msg.getString("help.page.display"));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(msgC.viaChat(true, this.msg.getString("plugin-error.row-not-valid")));
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

    /**
     * Construct the HashMap
     * */
    private void initHeMap() {
        heMap.put("/rxp help", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.help")))));
        heMap.put("/rxp create", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.create")))));
        heMap.put("/rxp check", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.check-specific")))));
        heMap.put("/rxp check-specific", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.check")))));
        heMap.put("/rxp goals", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.goals")))));
        heMap.put("/rxp score", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.score")))));
        heMap.put("/rxp scoreboard", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.scoreboard-gui")))));
        heMap.put("/rxp scoreboard-gui", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.scoreboard")))));
        heMap.put("/rxp set", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.set")))));
        heMap.put("/rxp maximum", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.maximum")))));
        heMap.put("/rxp redeem", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.redeem")))));
        heMap.put("/rxp reload", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.reload")))));
        heMap.put("/rxp reset", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.reset")))));
        heMap.put("/rxp reward", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.reward")))));
        heMap.put("/rxp secret", new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(chat.translateColors(msg.getString("help.hovers.commands.secret")))));
    }
}
