package com.sadshrimpy.referralxpert.referral;

import com.sadshrimpy.referralxpert.referral.subt.Period;
import com.sadshrimpy.referralxpert.referral.subt.Usages;

import java.util.UUID;

public class Referral {

    private Usages usages;
    private Period period;

    private UUID owner_uuid;
    private MultiansType once;
    private String code;
    /**
     * @param code the referral code that a player can redeem.
     * @param owner_uuid the UUID of the player that head the code (referral code).
     * @param per_period the time (in hours) between the redeem of a code
     * @param per_infinity is the period infinity? If YES the interval will be infinitive, no matter what value per_period will assume
     * @param usa_once is the code single time redeemable? If YES the code can be redeemed once per player
     * @param usa_usages global usages which the code can be used. If assume any value below 0 (inclusive) will be any times redeemable
     * */
    public Referral(String code, UUID owner_uuid, long per_period, MultiansType per_infinity, long usa_usages, MultiansType usa_once) {
        this.owner_uuid = owner_uuid;
        this.code = code;
        this.once = per_infinity;

        this.period = new Period(per_period, per_infinity);
        this.usages = new Usages(usa_usages, usa_once);
    }

    /** Getters */
    public Period getPeriod() {
        return period;
    }
    public Usages getUsages() {
        return usages;
    }

    public UUID getOwner_uuid() {
        return owner_uuid;
    }
    public MultiansType getOnce() {
        return once;
    }
    public String getCode() {
        return code;
    }
}
