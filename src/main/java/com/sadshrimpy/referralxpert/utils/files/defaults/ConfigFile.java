package com.sadshrimpy.referralxpert
.utils.files.defaults;

import com.sadshrimpy.referralxpert
.utils.files.DefaultFiles;
import com.sadshrimpy.referralxpert
.utils.files.handlers.YamlHandler;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class ConfigFile implements DefaultFiles {

    @Override
    public String getName() {
        return sadLibrary.files().getConfigName();
    }

    @Override
    public void perform() throws Exception {
        if (new YamlHandler(sadLibrary.files().getConfigName()).initialize())
            //sadLibrary.configurations().getConfigConfigurations();
            sadLibrary.configurations().setNewConfig();
        else
            sadLibrary.messages().viaConsole(true, "&rThe file: &f" + getName() + "&r &ccan't &rbe created.");
    }
}
