package com.shuai.hehe.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * 相册中单个图片的信息
 */
public class PicInfo {
    /**
     * 图片id
     */
    @JsonProperty("id")
    private int mId;
    
    /**
     * 该图片大图的url
     */
    @JsonProperty("bigUrl")
    private String mBigUrl;
    
    /**
     * 该图片的描述
     */
    @JsonProperty("description")
    private String mDescription;

    public PicInfo() {
    }
    
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getBigPicUrl() {
        return mBigUrl;
    }

    public void setBigPicUrl(String bigUrl) {
        this.mBigUrl = bigUrl;
    }

    public String getPicDescription() {
        return mDescription;
    }

    public void setPicDescription(String description) {
        this.mDescription = description;
    }

}
