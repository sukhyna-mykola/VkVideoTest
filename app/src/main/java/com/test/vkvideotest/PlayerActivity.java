package com.test.vkvideotest;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.test.vkvideotest.helpers.RequestHelper;
import com.test.vkvideotest.helpers.Utils;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

public class PlayerActivity extends AppCompatActivity {

    public static final String VIDEO_ID = "video_id";
    public static final String OWNER_ID = "owner_id";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int videoId = getIntent().getIntExtra(VIDEO_ID, 0);
        int ownerId = getIntent().getIntExtra(OWNER_ID, 0);

        mWebView = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyCustomWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        setContentView(mWebView);

        RequestHelper.getPlayer(ownerId, videoId, new APICallbacks() {
            @Override
            public void onResponse(VKResponse response) {
                String player = Utils.parseVideoPlayerJSON(response.json);
                if (player != null) {
                    mWebView.loadUrl(player);
                } else {
                    closePlayer();
                }

            }

            @Override
            public void onFailure(VKError error) {
                closePlayer();
            }
        });
    }


    private void closePlayer() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
