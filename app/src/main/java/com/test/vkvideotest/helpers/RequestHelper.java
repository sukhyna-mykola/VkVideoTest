package com.test.vkvideotest.helpers;

import android.app.Activity;
import android.content.Context;

import com.test.vkvideotest.APICallbacks;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;


public final class RequestHelper {


    public static void login(Activity mActivity){
        String[] scope = new String[]{VKScope.VIDEO,VKScope.WALL,VKScope.FRIENDS};
        VKSdk.login(mActivity, scope);
    }

    public static void logout(){
        VKSdk.logout();
    }

    public static void getVideos(String startFrom, final APICallbacks mCallbacks) {
        VKRequest request = new VKRequest("newsfeed.get",
                VKParameters.from(VKApiConst.FILTERS, "video", "count", 5, "start_from", startFrom));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                mCallbacks.onResponse(response);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                mCallbacks.onFailure(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });

    }

    public static void getPlayer(int ownerId, int videoId, final APICallbacks mCallbacks) {
        VKRequest request = VKApi.video().get(VKParameters.from("videos", ownerId + "_" + videoId));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                mCallbacks.onResponse(response);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                mCallbacks.onFailure(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

}
