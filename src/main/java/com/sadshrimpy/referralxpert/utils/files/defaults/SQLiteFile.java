package com.sadshrimpy.referralxpert.utils.files.defaults;

import com.sadshrimpy.referralxpert.utils.files.DefaultFiles;
import com.sadshrimpy.referralxpert.utils.files.handlers.YamlHandler;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class SQLiteFile  implements DefaultFiles {

    @Override
    public String getName() {
        return sadLibrary.files().getSqliteName();
    }

    @Override
    public void perform() throws Exception {
        if (new YamlHandler(sadLibrary.files().getSqliteName()).initialize())
            sadLibrary.configurations().setNewSQLite();
        else
            sadLibrary.chat().viaConsole(true, "&rThe file: &f" + getName() + "&r &ccan't &rbe created.");
    }
}

