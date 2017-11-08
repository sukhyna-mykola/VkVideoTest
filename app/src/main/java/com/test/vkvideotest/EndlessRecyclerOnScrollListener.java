package com.test.vkvideotest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal;
    private boolean loading;
    private int firstVisibleItemPosition, visibleItemCount, totalItemCount;


    public EndlessRecyclerOnScrollListener() {
        reset();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = recyclerView.getLayoutManager().getItemCount();
        firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItemPosition) {

            onLoadMore();

            loading = true;
        }
    }

    public void reset() {
        this.loading = true;
        this.previousTotal = 0;
    }

    public abstract void onLoadMore();
}