package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.utils.files.handlers.YamlHandler;
import org.bukkit.configuration.file.FileConfiguration;

public class SadConfigurations extends SadFiles {

    // Configurations
        // YML
            private FileConfiguration configConfiguration;
            private FileConfiguration messagesConfiguration;

        // JSON
    /*
            private List<CurrentGenerics> handler;
            private JsonHandler<CurrentGenerics> currentConfiguration;
     */

    public boolean reloadFiles() {
        // YML
        this.configConfiguration = new YamlHandler(super.configName).get();
        this.messagesConfiguration = new YamlHandler(super.messagesName).get();

        /*
        // JSON
        try {
            this.handler = new JsonHandler<CurrentGenerics>(CurrentGenerics.class, super.currentName).read();
        } catch (IOException e) {
            sadLibrary.messages().viaConsole(true, "&rThe file: &f" + super.currentName + "&r &ccan't &rbe reloaded.");
            throw new RuntimeException(e.getMessage());
        }
         */
        return true;
    }

    // Setters
    public void setNewConfig() {
        this.configConfiguration = new YamlHandler(super.configName).get();
    }
    public void setNewMessages() {
        this.messagesConfiguration = new YamlHandler(super.messagesName).get();
    }
    /*
    public void setNewCurrentConfig() throws IOException {

        this.currentConfiguration = new JsonHandler<>(CurrentFile.class, super.currentName);
        this.handler = new JsonHandler<CurrentGenerics>(CurrentGenerics.class, super.currentName).read();
    }
     */

    // Getters
    public FileConfiguration getConfig() {
        return this.configConfiguration;
    }
    public FileConfiguration getMessages() {
        return this.messagesConfiguration;
    }
    /*
    public JsonHandler<CurrentGenerics> getCurrentConfiguration() {
        return this.currentConfiguration;
    }
    public List<CurrentGenerics> getHandler() {
        return this.handler;
    }
     */

}
