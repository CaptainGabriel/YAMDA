package com.example.ricardopeixe.moviedb.views.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * This class represents the ViewHolder design pattern.
 * It was created in order to prevent calling the "findViewById" method too many times since
 * it decreases performance based on how big is the tree created by the UI elements
 * present in the view used by that class. In this case the there isn't a lot of elements
 * but is a good practice nonetheless.
 *
 *
 */
public class SearchListViewHolder {

    private View mParentView;

    private TextView mTitleTextView;
    private TextView mVoteAverageView;
    private ImageView mItemImage;
    private TextView mReleaseDateView;


    public ImageView mLikeIconView;

    public SearchListViewHolder(View parent) {
        mParentView = parent;
    }

    public ImageView getItemImageView() {
        return mItemImage;
    }

    public TextView getReleaseDateView() {
        return mReleaseDateView;
    }

    public ImageView getLikeIconView() {
        return mLikeIconView;
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getVoteAverageView() {
        return mVoteAverageView;
    }

    public View getParentView() {
        return mParentView;
    }

    public void setItemImage(ImageView itemImage) {
        mItemImage = itemImage;
    }

    public void setLikeIconView(ImageView likeIconView) {
        mLikeIconView = likeIconView;
    }

    public void setParentView(View parentView) {
        mParentView = parentView;
    }

    public void setReleaseDateView(TextView releaseDateView) {
        mReleaseDateView = releaseDateView;
    }

    public void setTitleTextView(TextView titleTextView) {
        mTitleTextView = titleTextView;
    }

    public void setVoteAverageView(TextView voteAverageView) {
        mVoteAverageView = voteAverageView;
    }
}


