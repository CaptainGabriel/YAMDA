package com.dev.moviedb.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.moviedb.mvvm.repository.local.db.entity.content.Movie;
import com.dev.moviedb.utils.DtoUtils;

import petegabriel.com.yamda.R;

/**
 * This class builds the view in order to show the details of a certain {@link Movie}.
 */
public class DetailMovieFragment extends AbstractDetailDataFragment<Movie> {


    /**
     * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle).
     * Attaching arguments to a fragment must be done after the fragment is created but before it is
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
     * @see "Android Programming: The Big Nerd Ranch Guide  # Attaching arguments to a fragment"
     */
    public static Fragment newInstance(Parcelable item) {
        Bundle args = new Bundle();
        args.putParcelable(SAVED_PARCELABLE_KEY, item);
        DetailMovieFragment frag = new DetailMovieFragment();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details_layout, container, false);

        //restore state if needed
        if (null != savedInstanceState && mToDetail == null) {
            mToDetail = savedInstanceState.getParcelable(SAVED_PARCELABLE_KEY);
        }

        //label of action bar. Might not be the right place to do this..
        getActivity().setTitle("\"" + mToDetail.getPrimaryFact().getTitle() + "\"");

        StringBuilder buffer = new StringBuilder();

        buffer.append("Title: " + mToDetail.getPrimaryFact().getOriginalTitle());
        ((TextView) view.findViewById(R.id.details_main_title)).setText(mToDetail.getPrimaryFact().getOriginalTitle());

        ((TextView) view.findViewById(R.id.details_description)).setText(mToDetail.getPrimaryFact().getOverview());

        buffer.append("Release Date: " + mToDetail);
        ((TextView) view.findViewById(R.id.details_release_date)).setText(mToDetail.toString());

        buffer.append("Vote Average: " + mToDetail.getPopularity().getVoteAverage());
        ((TextView) view.findViewById(R.id.details_classification)).setText(
                String.valueOf(mToDetail.getPopularity().getVoteAverage()));

        //Query for an image based upon the path.
        ImageView imgView = (ImageView) view.findViewById(R.id.details_main_picture);


        String humanReadableRuntime = DtoUtils.transformRuntime(mToDetail.getAdvancedData().getRuntime());
        setupElement(view, R.id.details_runtime, R.id.details_runtime_icon, humanReadableRuntime);


        setupElement(view, R.id.details_main_genre, NO_ICON, mToDetail.getAdvancedData().getGenres().toString());

        setupElement(view, R.id.tagline, NO_ICON, "\"" + mToDetail.getAdvancedData().getTagLine() + "\"");


        setupElement(view, R.id.imdb_textview, NO_ICON, mToDetail.getAdvancedData().getImdbId());

        startTrailer(view, mToDetail.getMovieTrailer().toString());
        Log.d(TAG, buffer.toString());
        return view;
    }
}
