package com.cahyocool.kafaadslibrary.data;

public enum AdServer {
    ONEDRIVE("ONEDRIVE", 1),
    DROPBOX("DROPBOX", 2),
    GDRIVE("GDRIVE", 3);

    private final String name;
    private final int code;

    AdServer(String name, int code) {
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
