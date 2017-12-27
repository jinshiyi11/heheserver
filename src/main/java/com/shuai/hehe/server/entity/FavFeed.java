package com.shuai.hehe.server.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 收藏的feed
 */
public class FavFeed extends Feed {
    @SerializedName("favId")
    private int mFavId;

    private long mUserId;

    public FavFeed(){

    }

    public FavFeed(Feed feed) {
        super(feed);
    }

    public int getFavId() {
        return mFavId;
    }

    public void setFavId(int favId) {
        this.mFavId = favId;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }
}
