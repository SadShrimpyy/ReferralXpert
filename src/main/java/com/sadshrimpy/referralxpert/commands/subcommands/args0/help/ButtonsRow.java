package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

import com.sadshrimpy.referralxpert.utils.sadlibrary.SadMessages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class ButtonsRow extends HelpCommand {

    private byte page;
    private byte pageMax;
    private TextComponent[] aTC = new TextComponent[2];

    public ButtonsRow(int page, int pageMax) {
        this.page = (byte) page;
        this.pageMax = (byte) pageMax;
    }

    /**
     * Build the single button
     * */
    private String getButton(String path) {
        StringBuilder sB = new StringBuilder();
        sB.setLength(0);
        sB.append(super.msgC.getString(path));;

        byte curIndex = (byte) sB.indexOf(super.place.getHelpCurPage());
        if (curIndex != -1)
            sB.replace(curIndex, curIndex + super.place.getHelpCurPage().length(), Byte.toString(page));

        curIndex = (byte) sB.indexOf(super.place.getHelpNextPage());
        if (curIndex != -1) {
            if (page < pageMax)
                sB.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), Byte.toString((byte) (page + 1)));
            else
                sB.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), ":c");
        }

        curIndex = (byte) sB.indexOf(super.place.getHelpPrevPage());
        if (curIndex != -1) {
            if (page > 1)
                sB.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), Byte.toString((byte) (page - 1)));
            else
                sB.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), ":c");
        }

        return sB.toString();
    }

    /**
     * Build all the row with the button
     */
    public TextComponent getRowHoverable(String path) {
        StringBuilder sB1 = new StringBuilder();
        SadMessages msg = sadLibrary.messages();
        HoverEvent[] aHE = new HoverEvent[2];

        // TODO: 5/26/2023 check the page and not go over the max :)
        ClickEvent[] aCE = new ClickEvent[2];
        if (page > 1) {
            aHE[0] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-previous.hover"))));
            aCE[0] = new ClickEvent(ClickEvent.Action.RUN_COMMAND, sB1.append("/rxp help ").append(page - 1).toString());
        } else
            aHE[0] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-previous.first-page"))));

        sB1.setLength(0);

        if (page < pageMax) {
            aHE[1] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-next.hover"))));
            aCE[1] = new ClickEvent(ClickEvent.Action.RUN_COMMAND, sB1.append("/rxp help ").append(page + 1).toString());
        } else
            aHE[1] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(msg.translateColors(msgC.getString("help.page.button-next.last-page"))));

        sB1.setLength(0);
        sB1.append(msgC.getString(path));

        String[] aS = new String[]{"", ""};
        byte[] b = getBoundaries(sB1);
        aS[0] = getSubString(sB1, b, "help.page.button-previous.text", super.place.getHelpBtnPrev());
        aS[1] = getSubString(sB1, b, "help.page.button-next.text", super.place.getHelpBtnNext());
        aTC[0] = constructComponent((byte) 0, msg.translateColors(aS[0]), aHE, aCE);
        aTC[1] = constructComponent((byte) 1, msg.translateColors(aS[1]), aHE, aCE);

        return constructComponent(sB1, msg);
    }

    /**
     * Construct the final component to be returned
     * */
    private TextComponent constructComponent(StringBuilder sB1, SadMessages msg) {
        byte[] ps = getBoundaries(sB1);
        TextComponent t = new TextComponent();
        t.addExtra(msg.translateColors(sB1.substring(0, ps[0])));
        t.addExtra(aTC[0]);
        t.addExtra(msg.translateColors(sB1.substring(ps[1] + 1, ps[2])));
        t.addExtra(aTC[1]);
        t.addExtra(msg.translateColors(sB1.substring(ps[3] + 1)));
        return t;
    }

    /**
     * Construct the substring with the placeholders
     * */
    private String getSubString(StringBuilder sB1, byte[] ps, String path, String place) {
        if (sB1.substring(ps[0], ps[1] + 1).equalsIgnoreCase(place))
            return sB1.substring(ps[0], ps[1] + 1).replace(place, getButton(path));
        else if (sB1.substring(ps[2], ps[3] + 1).equalsIgnoreCase(place))
            return sB1.substring(ps[2], ps[3] + 1).replace(place, getButton(path));
        return null;
    }

    /**
     * Construct the text component
     * */
    private TextComponent constructComponent(byte i, String button, HoverEvent[] aHE, ClickEvent[] aCE) {
        aTC[i] = new TextComponent();
        aTC[i].setText(button);
        aTC[i].setHoverEvent(aHE[i]);
        aTC[i].setClickEvent(aCE[i]);
        return aTC[i];
    }

    /**
     * Get the boundaries of the placeholders
     * */
    private byte[] getBoundaries(StringBuilder string) {
        byte[] percs = new byte[4];
        byte pos = 0;
        for (byte i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '%') {
                percs[pos] = i;
                pos++;
            }
        }
        return percs;
    }

}