package com.dev.moviedb.views.adapters;

import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.mvvm.data.source.remote.MovieApiProvider;
import com.dev.moviedb.storage.repo.IRepository;
import com.dev.moviedb.storage.repo.Repository;
import com.dev.moviedb.storage.repo.db.DataProvider;
import com.dev.moviedb.storage.repo.db.DataRecord;
import com.dev.moviedb.storage.repo.db.DataUnit;

import java.util.List;

import petegabriel.com.yamda.R;

/**
 * This class represents the adapter used by the fragment that displays
 * the upcoming movies. This adapter gives the possibility of classifying the movies as "favorites"
 * to the user.
 *
 * @version 1.0.0
 */
public class UpcomingItemListAdapter extends AbstractListAdapter<DataRecord> {

    public UpcomingItemListAdapter(Application ctx, List<DataRecord> items) {
        super(ctx, items);
    }

    /** {@inheritDoc} */
    @Override
    protected View inflateParticularLayout(Context ctx) {
        return LayoutInflater.from(ctx).inflate(R.layout.fragment_list_view_with_follow_layout, null);
    }

    /** {@inheritDoc} */
    @Override
    protected SearchListViewHolder buildViewHolder(View convertView) {
        SearchListViewHolder holder = new SearchListViewHolder(convertView);
        holder.setItemImage((ImageView) convertView.findViewById(R.id.card_image));
        holder.setTitleTextView((TextView) convertView.findViewById(R.id.title));
        holder.setLikeIconView((ImageView) convertView.findViewById(R.id.like_icon));
        return holder;
    }

    /** {@inheritDoc} */
    @Override
    protected void populateViewComponents(final Application ctx, final SearchListViewHolder holder, final int position) {

        final DataRecord itemToMap = getItem(position);
        MovieApiProvider fetcher = ((YamdaApplication) ctx).getApiFetcher();

        fetcher.loadImage(getContext(),
                itemToMap.getPosterImagePath(),
                MovieApiProvider.DEFAULT_IMG_SIZE,
                R.drawable.list_placeholder,
                holder.getItemImageView());

        int color = (itemToMap.isFavorite()) ? ctx.getResources().getColor(R.color.tumbleweed)
                : ctx.getResources().getColor(R.color.medium_grey);
        holder.mLikeIconView.setColorFilter(color);

        //title
        holder.getTitleTextView().setText(itemToMap.getOriginalTitle());

        //favorite 'hand' icon
        holder.mLikeIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((YamdaApplication) ctx.getApplicationContext())
                        .getRepository()
                        .getFavoriteRecordByID(itemToMap.getId(), handleFavoriteClick(holder, itemToMap));
            }
        });
    }


    /**
     * This method performs the delete query to remove a certain item from
     * the Favorite table.
     *
     * @param uri         The uri that identifies the resource to delete.
     * @param record      The object that mirrors the data in the database.
     * @param fromAdapter The object that mirrors the data in the adapter.
     * @param holder      The view holder.
     */
    private void deleteHandler(final Uri uri, final DataRecord record, final DataRecord fromAdapter, final SearchListViewHolder holder) {
        ((YamdaApplication) ctx).getRepository().deleteRecord(uri, record, new Completion<Void>() {
            @Override
            public void onResult(CallResult result) {
                try {
                    holder.mLikeIconView.setColorFilter(ctx.getResources().getColor(R.color.medium_grey));
                    fromAdapter.setFavorite(false);
                    Log.d("OnDeleteComplete",
                            "The movie with the ID " + record.getId() + " is no longer a favorite.");

                } catch (Exception _) {
                    Log.d("OnDeleteComplete", "The record could not be delete.");
                }
            }
        });
    }


    /**
     *
     * @param holder
     * @param itemToMap
     * @return
     */
    private Completion<DataRecord> handleFavoriteClick(final SearchListViewHolder holder, final DataRecord itemToMap) {
        return new Completion<DataRecord>() {
            @Override
            public void onResult(@NonNull CallResult<DataRecord> result) {
                try {
                    final DataRecord record = result.getResult();
                    Log.d("OnClickFavorite", "Clicked over a favorite heart icon.");

                    Uri uri = Repository.makeUri(DataUnit.Tables.FAVORITE_TABLE);
                    uri = ContentUris.withAppendedId(uri, record.getId());

                    deleteHandler(uri, record, itemToMap, holder);

                } catch (Exception e) {
                    Log.e("UpcomAdapter", "Movie is not marked as favorite.");

                    holder.mLikeIconView.setColorFilter(ctx.getResources().getColor(R.color.tumbleweed));
                    itemToMap.setFavorite(true);
                    //Update state in upcoming table and mark as favorite.
                    updateFavoriteStatusOfRecord(ctx, itemToMap);
                    Snackbar.make(holder.getParentView(), R.string.snackmsg_marked_favorite,
                            Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    /**
     * Update status in the upcoming table and add the record to the favorites.
     *
     * @param app    The application's context
     * @param record The record
     */
    private void updateFavoriteStatusOfRecord(Application app, DataRecord record) {IRepository repo = ((YamdaApplication) app).getRepository();
        //update in the "upcoming" table
        repo.updateFavoriteStatus(app.getApplicationContext(), DataProvider.UPCOMING_TABLE_NAME, record);
        //add to the favorites
        repo.insertRecord(DataProvider.FAVORITE_TABLE_NAME, record);
    }


}
