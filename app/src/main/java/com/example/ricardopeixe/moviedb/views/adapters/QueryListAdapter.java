package com.example.ricardopeixe.moviedb.views.adapters;

import com.example.ricardopeixe.moviedb.R;
import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.network.http.provider.MovieApiProvider;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        MovieApiProvider fetcher = ((YamdaApplication) ctx).getApiFetcher();
        fetcher.loadImage(getContext(), movie.getMovieImages().getPosterImagePath(),
                MovieApiProvider.DEFAULT_IMG_SIZE, R.drawable.list_placeholder, holder.getItemImageView());

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
