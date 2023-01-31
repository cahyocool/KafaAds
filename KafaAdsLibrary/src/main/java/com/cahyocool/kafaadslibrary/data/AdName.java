package com.cahyocool.kafaadslibrary.data;

public enum AdName {
    FACEBOOK("FACEBOOK", 2),
    ADMOB("ADMOB", 3),
    APPLOVIN("APPLOVIN", 4),
    UNITY("UNITY",5);

    private final String name;
    private final int code;

    AdName(String name, int code) {
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
