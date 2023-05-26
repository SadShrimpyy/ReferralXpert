package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

import com.sadshrimpy.referralxpert.utils.sadlibrary.SadMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Objects;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;
import static org.bukkit.Bukkit.spigot;

public class ButtonsRow extends HelpCommand {

    private int page;
    private int pageMax;
    private TextComponent[] aTC = new TextComponent[2];

    public ButtonsRow(int page, int pageMax) {
        this.page = page;
        this.pageMax = pageMax;
    }

    /** Build the single button */
    private String getButton(String path) {
        StringBuilder sB = new StringBuilder();
        sB.setLength(0);
        sB.append(super.msgC.getString(path));;

        int curIndex = sB.indexOf(super.place.getHelpCurPage());
        if (curIndex != -1)
            sB.replace(curIndex, curIndex + super.place.getHelpCurPage().length(), Integer.toString(page));

        curIndex = sB.indexOf(super.place.getHelpNextPage());
        if (curIndex != -1) {
            if (page < pageMax)
                sB.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), Integer.toString(page + 1));
            else
                sB.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), ":c");
        }

        curIndex = sB.indexOf(super.place.getHelpPrevPage());
        if (curIndex != -1) {
            if (page > 1)
                sB.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), Integer.toString(page - 1));
            else
                sB.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), ":c");
        }

        return sB.toString();
    }


    /**
     * Build all the row with the button
     */
    public BaseComponent[] getRowHoverable(String path, CommandSender sender) {
        StringBuilder sB1 = new StringBuilder();
        SadMessages msg = sadLibrary.messages();
        HoverEvent[] aHE = new HoverEvent[2];
        aHE[0] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-previous.hover"))));
        aHE[1] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-next.hover"))));

        // TODO: 5/26/2023 check the page and not go over the max :)
        ClickEvent[] aCE = new ClickEvent[2];
        sB1.setLength(0);
        aCE[0] = new ClickEvent(ClickEvent.Action.RUN_COMMAND, sB1.append("/rxp help ").append(page - 1).toString());
        sB1.setLength(0);
        aCE[1] = new ClickEvent(ClickEvent.Action.RUN_COMMAND, sB1.append("/rxp help ").append(page + 1).toString());

        aTC[0] = new TextComponent();
        aTC[1] = new TextComponent();

        sB1.setLength(0);
        sB1.append(msgC.getString(path));
        int[] ps = getRagexs(sB1);

        StringBuilder sB2 = sB1;
        String[] aS = new String[]{new String(), new String()};

        if (sB1.substring(ps[0], ps[1] + 1).equalsIgnoreCase(super.place.getHelpBtnPrev()))
            aS[0] = sB1.substring(ps[0], ps[1] + 1).replace(super.place.getHelpBtnPrev(), getButton("help.page.button-previous.text"));
        else if (sB1.substring(ps[0], ps[1] + 1).equalsIgnoreCase(super.place.getHelpBtnPrev()))
            aS[0] = sB1.substring(ps[2], ps[3] + 1).replace(super.place.getHelpBtnPrev(), getButton("help.page.button-previous.text"));

        ps = getRagexs(sB2);

        if (sB2.substring(ps[0], ps[1] + 1).equalsIgnoreCase(super.place.getHelpBtnNext()))
            aS[1] = sB2.substring(ps[0], ps[1] + 1).replace(super.place.getHelpBtnNext(), getButton("help.page.button-next.text"));
        else if (sB2.substring(ps[2], ps[3] + 1).equalsIgnoreCase(super.place.getHelpBtnNext()))
            aS[1] = sB2.substring(ps[2], ps[3] + 1).replace(super.place.getHelpBtnNext(), getButton("help.page.button-next.text"));


        aTC[0] = getComponent(0, msg.translateColors(aS[0]), aHE, aCE);
        aTC[1] = getComponent(1, msg.translateColors(aS[1]), aHE, aCE);

        Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).spigot().sendMessage(aTC[0], aTC[1]);

        return new BaseComponent[]{/*aTC[0], aTC[1]*/};
    }

    private TextComponent getComponent(int i, String button, HoverEvent[] aHE, ClickEvent[] aCE) {
        aTC[i] = new TextComponent();
        aTC[i].setText(button);
        aTC[i].setHoverEvent(aHE[i]);
        aTC[i].setClickEvent(aCE[i]);

        return aTC[i];
    }

    private int[] getRagexs(StringBuilder string) {
        int[] percs = new int[4];
        int pos = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '%') {
                percs[pos] = i;
                pos++;
            }
        }
        return percs;
    }

}