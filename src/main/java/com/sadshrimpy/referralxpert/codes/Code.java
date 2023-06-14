package com.sadshrimpy.referralxpert.codes;

import java.util.UUID;

public class Code {

    private double usages;
    private String code;
    private UUID ownerId;
    private int period;
    private boolean once;

    /**
     * @param code the referral code that a player can redeem.
     * @param usages the total usages that the code had.
     * @param ownerId the UUID of the player that head the code (referral code).
     * @param once is the code redeemable only once per player?
     *             If true : each player can redeem this code once.
     *             If false : a player can redeem this code usages time.
     * @param period the time (in hours) between the redeem of a code. If -1 or 0 the code will be redeemable once.
     * */
    public Code(String code, double usages, UUID ownerId, boolean once, String period) {
        this.ownerId = ownerId;
        this.usages = usages;
        this.once = once;
        this.code = code;
        this.period = once ? -1 : Integer.parseInt(period);
    }


    /** Getters */
    public UUID getOwnerId() {
        return ownerId;
    }
    public double getUsages() {
        return usages;
    }
    public String getCode() {
        return code;
    }
    public int getPeriod() {
        return period;
    }
    public boolean getOnce() {
        return once;
    }

    /** Setters */
}
