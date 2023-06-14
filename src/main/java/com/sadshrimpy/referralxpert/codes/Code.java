package com.sadshrimpy.referralxpert.codes;

import java.util.UUID;

public class Code {

    private double usages;
    private String code;
    private UUID ownerId;
    private int interval;
    private boolean once;

    public Code(String code, double usages, UUID ownerId, boolean once, String interval) {
        this.ownerId = ownerId;
        this.usages = usages;
        this.once = once;
        this.code = code;
        if (once)
            this.interval = -1;
        else
            this.interval = Integer.parseInt(interval);
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
    public int getInterval() {
        return interval;
    }
    public boolean getOnce() {
        return once;
    }

    /** Setters */
}
