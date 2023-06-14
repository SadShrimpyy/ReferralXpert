package com.sadshrimpy.referralxpert.codes;

import java.util.UUID;

public class Code {

    private double usages;
    private String code;
    private UUID ownerId;
    private int period;
    private boolean once;

    public Code(String code, double usages, UUID ownerId, boolean once, String period) {
        this.ownerId = ownerId;
        this.usages = usages;
        this.once = once;
        this.code = code;
        if (once)
            this.period = -1;
        else
            this.period = Integer.parseInt(period);
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
