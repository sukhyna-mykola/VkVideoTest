package com.test.vkvideotest.helpers;


import com.test.vkvideotest.models.Video;
import com.test.vkvideotest.models.VideosResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public final class Utils {
    public static VideosResponse parseVideosJSON(JSONObject json) {

        try {
            JSONObject response = json.getJSONObject("response");

            String nextFrom = response.getString("next_from");


            List<Video> videos = new LinkedList<>();

            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                JSONObject videoPackage = item.getJSONObject("video");

                JSONArray videoItems = videoPackage.getJSONArray("items");
                for (int j = 0; j < videoItems.length(); j++) {
                    JSONObject video = videoItems.getJSONObject(j);

                    int ownerId = video.getInt("owner_id");
                    int id = video.getInt("id");
                    String title = video.getString("title");
                    String photo = video.getString("photo_320");
                    int duration = video.getInt("duration");

                    videos.add(new Video(ownerId, id, title, photo, duration));

                }


            }
            VideosResponse result = new VideosResponse(videos, nextFrom);
            return result;
        } catch (JSONException mE) {
            mE.printStackTrace();
        }
        return null;
    }

    public static String parseVideoPlayerJSON(JSONObject json) {

        try {
            JSONObject response = json.getJSONObject("response");
            int count = response.getInt("count");
            if (count == 1) {
                JSONArray items = response.getJSONArray("items");

                JSONObject item = items.getJSONObject(0);
                String player = item.getString("player");

                return player;

            }

        } catch (JSONException mE) {
            mE.printStackTrace();
        }
        return null;
    }

    public static String formatDuration(int duration) {
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int seconds = duration % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
}