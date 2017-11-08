package com.test.vkvideotest.helpers;

import com.test.vkvideotest.models.Video;

import java.util.ArrayList;
import java.util.List;

public class VideosManager {

    private List<Video> mVideos;
    private String startFrom;

    public VideosManager() {
        mVideos = new ArrayList<>();
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public int getVideosNumber() {
        return mVideos.size();
    }

    public boolean isEmpty() {
        return mVideos.isEmpty();
    }

    public void clearVideos() {
        mVideos.clear();
    }

    public void addVideos(List<Video> newVideos) {
        mVideos.addAll(newVideos);
    }

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String mStartFrom) {
        startFrom = mStartFrom;
    }
}
