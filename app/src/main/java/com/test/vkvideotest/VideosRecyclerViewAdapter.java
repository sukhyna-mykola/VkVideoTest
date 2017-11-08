package com.test.vkvideotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.vkvideotest.helpers.Utils;
import com.test.vkvideotest.models.Video;

import java.util.List;

public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosRecyclerViewAdapter.VideoViewHolder> {

    public final String TAG = getClass().getSimpleName();

    private final List<Video> mValues;

    private final Context mContext;
    private final OnClickItemListener mOnClickItemListener;

    public VideosRecyclerViewAdapter(List<Video> items, OnClickItemListener mOnClickItemListener, final Context mContext) {
        this.mValues = items;
        this.mContext = mContext;
        this.mOnClickItemListener = mOnClickItemListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false));

    }


    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        final Video mVideo = mValues.get(position);

        holder.mNameView.setText(mVideo.getTitle());

        holder.mDurationView.setText(Utils.formatDuration(mVideo.getDuration()));

        Picasso.with(mContext)
                .load(mVideo.getThumbnail())
                .into(holder.mThumbnailView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickItemListener.onClickItemListener(mVideo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDurationView;
        public final ImageView mThumbnailView;

        public VideoViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mDurationView = view.findViewById(R.id.duration);
            mThumbnailView = view.findViewById(R.id.thumbnail);
        }
    }

    public interface OnClickItemListener {
        void onClickItemListener(Video item);
    }
}
