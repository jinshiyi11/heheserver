package com.shuai.hehe.server.entity;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Feed {
    @SerializedName("id")
    private int mId;
    
    @SerializedName("type")
    private int mType;
    
    @SerializedName("title")
    private String mTitle;
    
    @SerializedName("content")
    private String mContent;
    
    @SerializedName("from")
    private int mFrom;
    
    @SerializedName("showTime")
    private long mShowTime;
    
    //public int mState;
    /**
     * 该新鲜事不展示给用户
     */
    public static int STATE_HIDDEN=0;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public int getFrom() {
        return mFrom;
    }

    public void setFrom(int from) {
        this.mFrom = from;
    }

    public long getShowTime() {
        return mShowTime;
    }

    public void setShowTime(long showTime) {
        this.mShowTime = showTime;
    }
    
    
}
