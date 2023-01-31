package com.cahyocool.kafaadslibrary.data;

import com.google.gson.annotations.SerializedName;

public class Ad {
    @SerializedName("name")
    public AdName name;
    @SerializedName("type")
    public AdType type;
    @SerializedName("key")
    public String key;
    @SerializedName("icon")
    public String icon;
    @SerializedName("title")
    public String title;
    @SerializedName("subTitle")
    public String subTitle;
    @SerializedName("image")
    public String image;


    public Ad(AdName name,
              AdType type,
              String key) {
        this.name = name;
        this.type = type;
        this.key = key;
    }
}
