package com.sadshrimpy.referralxpert.utils.files.defaults;

import com.sadshrimpy.referralxpert.utils.files.DefaultFiles;
import com.sadshrimpy.referralxpert.utils.files.handlers.YamlHandler;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;


public class MessagesFile implements DefaultFiles {

    @Override
    public String getName() {
        return sadLibrary.files().getMessagesName();
    }

    @Override
    public void perform() throws Exception {
        if (new YamlHandler(sadLibrary.files().getMessagesName()).initialize())
            //sadLibrary.configurations().getMessagesConfigurations();
            sadLibrary.configurations().setNewMessages();
        else
            sadLibrary.chat().viaConsole(true, "&rThe file: &f" + getName() + "&r &ccan't &rbe created.");
    }
}
