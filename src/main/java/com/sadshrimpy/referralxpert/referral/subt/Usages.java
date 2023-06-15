package com.sadshrimpy.referralxpert.referral.subt;

import com.sadshrimpy.referralxpert.referral.MultiansType;

public class Usages {
    private long period;
    private MultiansType once;

    /**
     * The USAGES are the SQL table. They represent the usages of a code.
     * @param period global usages which the code can be used. If assume any value below 0 (inclusive) will be any times redeemable
     * @param once is the code single time redeemable? If YES the code can be redeemed once per player
     * */
    public Usages(long period, MultiansType once) {
        this.period = period;
        this.once = once;
    }

    /** Getters */
    public long getPeriod() {
        return period;
    }
    public MultiansType getOnce() {
        return once;
    }
}
