package com.sadshrimpy.referralxpert.codes;

public class Codes {

    private String code;
    private double usages;

    public Codes(String code, double usages) {
        this.code = code;
        this.usages = usages;
    }

    /** Getters */
    public String getCode() {
        return code;
    }
    public double getUsages() {
        return usages;
    }

    /** Setters */
}
