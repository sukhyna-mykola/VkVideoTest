package com.test.vkvideotest.models;

public class Video {

    private int ownerId;
    private int videoId;
    private String title;
    private String thumbnail;
    private int duration;

    public Video(int mOwnerId, int mVideoId, String mTitle, String mThumbnail, int mDuration) {
        ownerId = mOwnerId;
        videoId = mVideoId;
        title = mTitle;
        thumbnail = mThumbnail;
        duration = mDuration;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getDuration() {
        return duration;
    }

    public int getVideoId() {
        return videoId;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
