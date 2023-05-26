package com.sadshrimpy.referralxpert.commands.subcommands.args0.help;

public enum RowPosition {
    LAST_ROW("LAST_ROW"),
    FIRST_ROW("FIRST_ROW");

    private String value;

    RowPosition(String value) {
        this.value = value;
    }

    public boolean isFirst(RowPosition row) {
        return row.equals(FIRST_ROW);
    }
}
