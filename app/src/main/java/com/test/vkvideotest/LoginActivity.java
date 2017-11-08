package com.test.vkvideotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.vkvideotest.helpers.RequestHelper;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "LoginActivity";

    private ProgressBar wait;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wait = (ProgressBar) findViewById(R.id.progres_wait_start_activity);
        enter = (Button) findViewById(R.id.enter_start_activity);

        enter.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d(TAG, "Success");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(VKError error) {
                setResult(RESULT_CANCELED);
                Log.d(TAG, "Error: " + error.errorMessage);
                finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_start_activity:

                RequestHelper.login(this);

                wait.setVisibility(View.VISIBLE);
                enter.setEnabled(false);

                break;
        }
    }
}
