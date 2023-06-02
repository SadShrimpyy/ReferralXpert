package com.sadshrimpy.referralxpert
.utils.files.handlers;

import org.bukkit.configuration.file.FileConfiguration;

interface FilesIn {
    FileConfiguration get();
    boolean exixts();
    void reload();
    void save();
    boolean initialize() throws Exception;
}
