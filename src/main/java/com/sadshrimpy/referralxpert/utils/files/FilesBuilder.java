package com.sadshrimpy.referralxpert.utils.files;

import com.sadshrimpy.referralxpert.utils.files.defaults.*;

import java.io.File;
import java.util.ArrayList;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class FilesBuilder {

    private ArrayList<DefaultFiles> defaultFiles = new ArrayList<>();

    {
        defaultFiles.add(new ConfigFile());
        defaultFiles.add(new MessagesFile());
        defaultFiles.add(new SQLiteFile());
    }

    public FilesBuilder() {
        if (!sadLibrary.generics().getPluginFolder().exists())
            sadLibrary.generics().getPluginFolder().mkdirs();
        filesBuilder();
        //dirBuilder();
        checkConfigVersion();
    }

    private void filesBuilder() {
        for (DefaultFiles fileIndex : defaultFiles) {
            try {
                fileIndex.perform();
            } catch (Exception e) {
                sadLibrary.messages().viaConsole(true, "&rThe file: &f" + fileIndex.getName() + "&r &ccan't &rbe initialized.");
                throw new RuntimeException(e);
            }
        }
    }

    private void dirBuilder() {
        // TODO: 24/02/2023 Directory getter with their path
        /*
        File databases = new File(sadLibrary.getGenerics().getPlugin().getDataFolder(), sadLibrary.getFiles().getDirectories());
        if (!databases.exists())
            databases.mkdir();
         */
    }

    @SuppressWarnings("DataFlowIssue")
    private void checkConfigVersion() {
        // TODO: 08/02/2023 check previus version (once is uploaded)
        for (File file : sadLibrary.generics().getPluginFolder().listFiles()) // Goes through ALL files
            if (file.getName().equalsIgnoreCase(sadLibrary.files().getConfigName())) { // if is config.yml - (not Case sensitive)
                if (!sadLibrary.configurations().getConfig().getString("version").equals(sadLibrary.generics().getVersion())) {
                    // WRONG version
                    // TODO: 24/02/2023 Check if is one of the previous (once uploaded) then apply the new changes. Else remove the config:
                    sadLibrary.messages().viaConsole(true, "&cThe plugin version is incorrect." +
                            "&cThe file: &econfig.yml&c, has been &ericarged &rusing &edefault values&c.");
                    sadLibrary.generics().getPlugin().saveResource(file.getName(), true);
                }
            }
    }
}
