package com.cahyocool.kafaadslibrary.data;

public enum AdType {
    BANNER("BANNER", 101),
    HALF_BANNER("HALF_BANNER", 103),
    INTERSTITIAL("INTERSTITIAL", 107),
    NATIVE("NATIVE", 109),
    REWARD("REWARD", 110);

    final private String name;
    final private int code;

    AdType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
