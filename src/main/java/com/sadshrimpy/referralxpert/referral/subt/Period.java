package com.sadshrimpy.referralxpert.referral.subt;

import com.sadshrimpy.referralxpert.referral.MultiansType;

public class Period {

    private long period;
    private MultiansType infinity;

    /**
     * The PERIOD are the SQL table. They represent the usages of a code.
     * @param period the time (in hours) between the redeem of a code
     * @param infinity is the period infinity? If YES the interval will be infinitive, no matter what value per_period will assume
     * */
    public Period(long period, MultiansType infinity) {
        this.period = period;
        this.infinity = infinity;
    }

    public long getPeriod() {
        return period;
    }
    public MultiansType getInfinity() {
        return infinity;
    }
}
