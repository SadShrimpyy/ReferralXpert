package com.sadshrimpy.referralxpert.utils.sadlibrary;

import com.sadshrimpy.referralxpert.codes.Code;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SadReferrals {

    private HashMap<UUID, Date> onlineTime;
    private HashMap<String, Code> codes;

    public SadReferrals() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.codes = new HashMap<>();
    }

    public HashMap<String, Code> getCodes() { return this.codes; }
    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }

}
