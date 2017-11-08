package com.test.vkvideotest;


import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

public interface APICallbacks {
    void onResponse(VKResponse response);

    void onFailure(VKError error);
}
