package com.sadshrimpy.referralxpert.referral;

public enum MultiansType {
    YES("YES"),
    NO("NO");

    private String type;

    MultiansType(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}
