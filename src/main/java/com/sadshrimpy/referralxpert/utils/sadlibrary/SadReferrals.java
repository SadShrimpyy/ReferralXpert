package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.codes.Codes;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SadReferrals {

    private HashMap<UUID, Date> onlineTime;
    private HashMap<String, Codes> codes;

    public SadReferrals() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.codes = new HashMap<>();
    }

    public HashMap<String, Codes> getCodes() { return this.codes; }
    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }

}
