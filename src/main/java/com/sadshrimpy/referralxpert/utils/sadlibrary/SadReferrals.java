package com.sadshrimpy.referralxpert.utils.sadlibrary;

import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class SadReferrals {

    private HashMap<UUID, Date> onlineTime;
    private HashSet<String> codes;

    public SadReferrals() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.codes = new HashSet<>();
    }

    public HashSet<String> getCodes() { return this.codes; }
    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }

}
