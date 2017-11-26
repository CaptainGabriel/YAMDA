package com.dev.moviedb.views.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.moviedb.mvvm.model.movies.Movie;

import java.util.List;

import petegabriel.com.yamda.R;

/**
 * Custom adapter to be used by the components that need to display data coming
 * from lists with objects of type {@link Movie}.
 *
 * @version 1.0.0
 */
public class QueryListAdapter extends AbstractListAdapter<Movie> {

    public QueryListAdapter(Application context, List<Movie> objects) {
        super(context, objects);
    }

    @Override
    protected void populateViewComponents(Application ctx, SearchListViewHolder holder, int pos) {
        Movie movie = getItem(pos);

        holder.getTitleTextView()
                .setText(movie.getPrimaryFacts().getOriginalTitle());
        holder.getVoteAverageView()
                .setText(String.valueOf(movie.getPopularity().getVoteAverage()));
    }

    @Override
    protected SearchListViewHolder buildViewHolder(View convertView) {
        SearchListViewHolder holder = new SearchListViewHolder(convertView);
        holder.setItemImage((ImageView)convertView.findViewById(R.id.card_image));
        holder.setTitleTextView((TextView) convertView.findViewById(R.id.title));
        holder.setVoteAverageView((TextView) convertView.findViewById(R.id.vote_average_textview));
        return holder;
    }

    @Override
    protected View inflateParticularLayout(Context ctx) {
        return LayoutInflater.from(ctx).inflate(R.layout.fragment_list_item_view_layout, null);
    }
}
