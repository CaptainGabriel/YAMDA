package com.example.dev.moviedb.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.network.http.provider.MovieApiProvider;
import com.example.dev.moviedb.storage.repo.db.DataRecord;
import com.example.dev.moviedb.utils.DtoUtils;

import petegabriel.com.yamda.R;

/**
 * This class builds the view in order to show the details of a certain {@link DataRecord}.
 *
 * @version 0.0.1
 */
public class DetailRecordFragment extends AbstractDetailDataFragment<DataRecord> {

    /**
     * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle).
     * Attaching arguments to a fragment must be done after the fragment is created but before it
     * is
     * added to an activity.
     *
     * To hit this window, Android programmers follow a convention of adding a static method named
     * newInstance() to the Fragment class.
     * This method creates the fragment instance and bundles up and sets its arguments.
     *
     * When the hosting activity needs an instance of that fragment,
     * you have it call the newInstance() method rather than calling the constructor directly.
     * The activity can pass in any required parameters to newInstance(...) that the fragment needs
     * to create its arguments.
     *
     * @param item Parcelable instance to save
     * @return DetailRecordFragment instance with arguments
     */
    public static Fragment newInstance(Parcelable item) {
        Bundle args = new Bundle();
        args.putParcelable(SAVED_PARCELABLE_KEY, item);
        DetailRecordFragment frag = new DetailRecordFragment();
        frag.setArguments(args);
        return frag;
    }


    /** {@inheritDoc} */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie_details_layout, container, false);

        //restore state if needed
        if (null != savedInstanceState) {
            mToDetail = savedInstanceState.getParcelable(SAVED_PARCELABLE_KEY);
        }

        getActivity().setTitle("\"" + mToDetail.getOriginalTitle() + "\"");
        StringBuilder buffer = new StringBuilder();

        //setup view with primary facts about this movie
        setupPrimaryFactsOfMovie(view, buffer);

        //try to add more info from advanced facts of this movie
        setupAdvancedFacts(view, buffer);

        startTrailer(view, mToDetail.getTrailerUrl());
        Log.d(TAG, buffer.toString());
        return view;
    }


    private void setupPrimaryFactsOfMovie(View view, StringBuilder internalBuffer) {
        internalBuffer.append("Title: " + mToDetail.getOriginalTitle());
        ((TextView) view.findViewById(R.id.details_main_title)).setText(mToDetail.getOriginalTitle());

        ((TextView) view.findViewById(R.id.details_description)).setText(mToDetail.getOverview());

        internalBuffer.append("Release Date: " + mToDetail.getReleaseDate());
        ((TextView) view.findViewById(R.id.details_release_date)).setText(mToDetail.getReleaseDate());

        internalBuffer.append("Vote Average: " + mToDetail.getVoteAverage());
        ((TextView) view.findViewById(R.id.details_classification)).setText(
                String.valueOf(mToDetail.getVoteAverage()));

        //Query for an image based upon the path and a custom image size.
        MovieApiProvider fetcher = ((YamdaApplication) getActivity().getApplication()).getApiFetcher();
        ImageView imgView = (ImageView) view.findViewById(R.id.details_main_picture);

        fetcher.loadImage(getActivity(), mToDetail.getBackdropImagePath(),
                MovieApiProvider.DETAILS_IMG_SIZE, R.drawable.details_placeholder, imgView);
    }

    private void setupAdvancedFacts(View view, StringBuilder internalBuffer) {

        if(mToDetail.getRuntime() != 0) {
            String humanReadableRuntime = DtoUtils.transformRuntime(mToDetail.getRuntime());
            setupElement(view, R.id.details_runtime, R.id.details_runtime_icon, humanReadableRuntime);
        }


        if(mToDetail.getStatus() != null)
            setupElement(view, R.id.details_status, R.id.details_status_icon, mToDetail.getStatus());

        if(mToDetail.getGenres() != null){
            setupElement(view, R.id.details_main_genre, NO_ICON, mToDetail.getGenres());
        }

        if(mToDetail.getTagline() != null)
            setupElement(view, R.id.tagline, NO_ICON, "\""+mToDetail.getTagline()+"\"");


        if(mToDetail.getImdbId() != null)
            setupElement(view, R.id.imdb_textview, NO_ICON, mToDetail.getImdbId());



    }

}
