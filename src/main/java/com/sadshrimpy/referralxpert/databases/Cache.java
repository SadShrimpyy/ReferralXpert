package com.sadshrimpy.referralxpert.databases;

import com.sadshrimpy.referralxpert.codes.Code;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Cache {
    private HashMap<UUID, Date> onlineTime;
    private HashMap<String, Code> codes;
    private HashMap<UUID, Long> playersTime;

    public Cache() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.codes = new HashMap<>();
        this.playersTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
    }

    public HashMap<String, Code> getCodes() {
        return this.codes;
    }
    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }

    /** Update maps */
    /**
     * It contains all the long times that need to be updated in the db
     * */
    public HashMap<UUID, Long> playersTimes() {
        return this.playersTime;
    }

    /** Clear the cache */
    public void clear() {
        playersTime.clear();
    }
}
