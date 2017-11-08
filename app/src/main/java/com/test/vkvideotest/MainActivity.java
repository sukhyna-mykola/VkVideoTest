package com.test.vkvideotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.test.vkvideotest.helpers.RequestHelper;
import com.test.vkvideotest.helpers.Utils;
import com.test.vkvideotest.helpers.VideosManager;
import com.test.vkvideotest.models.Video;
import com.test.vkvideotest.models.VideosResponse;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

public class MainActivity extends AppCompatActivity implements VideosRecyclerViewAdapter.OnClickItemListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int REQUEST_CODE = 1;

    protected VideosManager manager;

    private RecyclerView.Adapter adapter;
    private EndlessRecyclerOnScrollListener onScrollListener;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new VideosManager();
        
        adapter = new VideosRecyclerViewAdapter(manager.getVideos(), this, this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.videos_list);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        onScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                startLoading();
            }
        };

        recyclerView.addOnScrollListener(onScrollListener);


        if (!VKSdk.isLoggedIn()) {
            startLoginActivity();
        } else {
            startLoading();
        }

    }

    private void startLoginActivity() {
        Intent startActivity = new Intent(this, LoginActivity.class);
        startActivityForResult(startActivity, REQUEST_CODE);
    }


    private void startLoading() {
        swipeRefreshLayout.setRefreshing(true);

        RequestHelper.getVideos(manager.getStartFrom(), new APICallbacks() {
            @Override
            public void onResponse(VKResponse response) {

                VideosResponse videosResponse = Utils.parseVideosJSON(response.json);

                if (videosResponse != null) {

                    int from = manager.getVideosNumber();

                    manager.addVideos(videosResponse.getItems());
                    manager.setStartFrom(videosResponse.getNextFrom());

                    adapter.notifyItemInserted(from);
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(VKError error) {
                Toast.makeText(MainActivity.this, getString(R.string.error, error.errorMessage), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                RequestHelper.logout();

                startLoginActivity();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startLoading();
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public void onRefresh() {

        manager.clearVideos();
        manager.setStartFrom(null);

        onScrollListener.reset();

        adapter.notifyDataSetChanged();

        startLoading();
    }

    @Override
    public void onClickItemListener(Video item) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.VIDEO_ID, item.getVideoId());
        intent.putExtra(PlayerActivity.OWNER_ID, item.getOwnerId());
        startActivity(intent);
    }
}
