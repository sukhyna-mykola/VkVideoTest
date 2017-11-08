package com.test.vkvideotest.models;


import java.util.List;

public class VideosResponse {


    private List<Video> items;
    private String nextFrom;

    public VideosResponse(List<Video> mItems, String mNextFrom) {
        items = mItems;
        nextFrom = mNextFrom;
    }

    public List<Video> getItems() {
        return items;
    }

    public void setItems(List<Video> items) {
        this.items = items;
    }

    public String getNextFrom() {
        return nextFrom;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }

}
