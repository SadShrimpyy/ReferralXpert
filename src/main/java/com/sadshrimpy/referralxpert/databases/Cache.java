package com.sadshrimpy.referralxpert.databases;

import com.sadshrimpy.referralxpert.databases.queries.DBExecStmt;
import com.sadshrimpy.referralxpert.databases.queries.DBPreStmt;
import com.sadshrimpy.referralxpert.referral.Referral;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class Cache {
    private HashMap<UUID, Date> onlineTime;

    /** Update maps - Maps used to interact with the database */
    private HashMap<UUID, Long> playersTime;
    private HashMap<String, Referral> referralsCreated;
    private LinkedList<String> allDBReferrals;
    private HashMap<String, Referral> referralsUsed;

    public Cache() {
        this.onlineTime = new HashMap<>(Bukkit.getOnlinePlayers().size());

        /** Update maps - Maps used to interact with the database */
        this.playersTime = new HashMap<>(Bukkit.getOnlinePlayers().size());
        this.allDBReferrals = getAllRegisteredReferrals();
        this.referralsCreated = new HashMap<>();
        this.referralsUsed = new HashMap<>();
    }

    private LinkedList<String> getAllRegisteredReferrals() {
        sadLibrary.database().open();
        LinkedList<String> allRegisteredReferrals = new DBExecStmt(sadLibrary.database().getConnection(), new DBPreStmt()).getAllRegisteredReferrals();
        sadLibrary.database().close();
        return allRegisteredReferrals;
    }

    public HashMap<UUID, Date> getOnlineMap() {
        return this.onlineTime;
    }

    /** Update maps - Maps used to interact with the database */
    /**
     * @implNote contains all the long times that need to be updated in the db
     * */
    public HashMap<UUID, Long> CACHE_playersTimes() {
        return this.playersTime;
    }
    /**
     * @return all the referrals registered in the DB
     * */
    public LinkedList<String> DB_allReferrals() {
        return this.allDBReferrals;
    }
    /**
     * @return the referrals created but NOT the registered in the DB
     * */
    public HashMap<String, Referral> CACHE_referralsCreated() {
        return this.referralsCreated;
    }
    /**
     * @return all the referrals claimed by a player, within the CACHE
     * */
    public HashMap<String, Referral> CACHE_referralsClaimed() {
        return this.referralsUsed;
    }

    /** Clearer of the cache */
    public void clearAllCache() {
        playersTime.clear();
        referralsCreated.clear();
        referralsUsed.clear();
    }
    public void CACHE_clearPlayersTimes() {
        playersTime.clear();
    }
    public void CACHE_clearReferralsCreated() {
        referralsCreated.clear();
    }
    public void CACHE_clearReferralsUsed() {
        referralsUsed.clear();
    }
}
