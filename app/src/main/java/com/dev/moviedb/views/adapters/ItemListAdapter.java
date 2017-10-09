package com.dev.moviedb.views.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.network.http.provider.MovieApiProvider;
import com.dev.moviedb.storage.repo.db.DataRecord;

import java.util.List;

import petegabriel.com.yamda.R;

/**
 * This private class is the adapter that builds the views presented by the fragment.
 *
 * @version 1.0.0
 */
public class ItemListAdapter extends AbstractListAdapter<DataRecord> {

    public ItemListAdapter(Application ctx, List<DataRecord> items) {
        super(ctx, items);
    }

    @Override
    protected void populateViewComponents(Application ctx, SearchListViewHolder holder, int pos) {
        DataRecord itemToMap = getItem(pos);
        MovieApiProvider fetcher = ((YamdaApplication) ctx).getApiFetcher();
        fetcher.loadImage(getContext(), itemToMap.getPosterImagePath(),
                MovieApiProvider.DEFAULT_IMG_SIZE, R.drawable.list_placeholder, holder.getItemImageView());

        holder.getTitleTextView().setText(itemToMap.getOriginalTitle());
        holder.getVoteAverageView().setText(String.valueOf(itemToMap.getVoteAverage()));
    }

    @Override
    protected SearchListViewHolder buildViewHolder(View convertView) {
        SearchListViewHolder holder = new SearchListViewHolder(convertView);
        holder.setItemImage((ImageView) convertView.findViewById(R.id.card_image));
        holder.setTitleTextView((TextView) convertView.findViewById(R.id.title));
        holder.setVoteAverageView((TextView) convertView.findViewById(R.id.vote_average_textview));
        return holder;
    }

    @Override
    protected View inflateParticularLayout(Context ctx) {
        return LayoutInflater.from(ctx).inflate(R.layout.fragment_list_item_view_layout, null);
    }
}