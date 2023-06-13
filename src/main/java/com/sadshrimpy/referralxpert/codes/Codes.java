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

    public double getUsages() {
        return usages;
    }
    /** Getters */
    public String getCode() {
        return code;
    }
    public UUID getOwnerId() {
        return ownerId;
    }

    /** Setters */
}
