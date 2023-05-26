package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

public class ButtonsRow extends HelpCommand {

    int page;
    int pageMax;
    public ButtonsRow(int page, int pageMax) {
        this.page = page;
        this.pageMax = pageMax;
    }

    /** Build the single button */
    String getButton(String path) {
        String originalString = super.msg.getString(path);
        StringBuilder button = new StringBuilder(originalString);

        System.out.printf("Page %d AND pageMax %d\n", page, pageMax);

        int curIndex = button.indexOf(super.place.getHelpCurPage());
        if (curIndex != -1)
            button.replace(curIndex, curIndex + super.place.getHelpCurPage().length(), Integer.toString(page));

        curIndex = button.indexOf(super.place.getHelpNextPage());
        if (curIndex != -1) {
            if (page < pageMax)
                button.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), Integer.toString(page + 1));
            else
                button.replace(curIndex, curIndex + super.place.getHelpNextPage().length(), ":c");
        }

        curIndex = button.indexOf(super.place.getHelpPrevPage());
        if (curIndex != -1) {
            if (page > 1)
                button.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), Integer.toString(page - 1));
            else
                button.replace(curIndex, curIndex + super.place.getHelpPrevPage().length(), ":c");
        }

        return button.toString();
    }


    /** Build all the row with the button*/
    public String getRowHoverable(String path) {
        String row = super.msg.getString(path)
                .replace(super.place.getHelpBtnNext(), getButton("help.page.button-next"))
                .replace(super.place.getHelpBtnPrev(), getButton("help.page.button-previous"));
        return row;
    }
}
