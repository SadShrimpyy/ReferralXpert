package com.sadshrimpy.referralxpert.databases;

import com.sadshrimpy.referralxpert.referral.Referral;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Cache {
    private HashMap<UUID, Date> onlineTime;
    private HashMap<String, Referral> referrals;

    /** Update maps - Maps used to interact with the database */
    private HashMap<UUID, Long> playersTime;
    private HashMap<String, Referral> referralsCreated;
    private HashMap<String, Referral> referralsUsed;

    public Cache() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.referrals = new HashMap<>();

        /** Update maps - Maps used to interact with the database */
        this.playersTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.referralsCreated = new HashMap<>();
        this.referralsUsed = new HashMap<>();
    }

    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }
    public HashMap<String, Referral> getReferrals() {
        return this.referrals;
    }

    /** Update maps - Maps used to interact with the database */
    /**
     * It contains all the long times that need to be updated in the db
     * */
    public HashMap<UUID, Long> playersTimes() {
        return this.playersTime;
    }
    public HashMap<String, Referral> codeCreated() {
        return this.referralsCreated;
    }
    public HashMap<String, Referral> codeUsed() {
        return this.referralsUsed;
    }

    /** Clear the cache */
    public void clearAllCache() {
        playersTime.clear();
        referralsCreated.clear();
        referralsUsed.clear();
    }
    public void clearPlayersTimesCache() {
        playersTime.clear();
    }
    public void clearReferralsCreatedCache() {
        referralsCreated.clear();
    }
    public void clearReferralsUsedCache() {
        referralsUsed.clear();
    }
}
