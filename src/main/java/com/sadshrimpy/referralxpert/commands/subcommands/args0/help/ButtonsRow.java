package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

public class ButtonsRow extends HelpCommand {

    public ButtonsRow() {}

    String getButton(String path) {
        String button = super.msg.getString(path)
                .replace(super.place.getHelpCurPage(), Integer.toString(super.page))
                .replace(super.place.getHelpNextPage(), Integer.toString(super.page + 1))
                .replace(super.place.getHelpPrevPage(), Integer.toString(super.page - 1));

        return button;
    }

    public String getRowHoverable(String path) {
        String row = super.msg.getString(path)
                .replace(super.place.getHelpBtnNext(), getButton("help.page.button-next"))
                .replace(super.place.getHelpBtnPrev(), getButton("help.page.button-previous"));
        return row;
    }
}
