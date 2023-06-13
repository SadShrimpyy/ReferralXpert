package com.sadshrimpy.referralxpert.codes;

import java.util.UUID;

public class Codes {

    private double usages;
    private String code;
    private UUID ownerId;

    public Codes(String code, double usages, UUID ownerId) {
        this.ownerId = ownerId;
        this.usages = usages;
        this.code = code;
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

    /** Setters */
}
