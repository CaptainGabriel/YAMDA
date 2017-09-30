package com.example.dev.moviedb.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.model.Movie;
import com.example.dev.moviedb.network.http.provider.MovieApiProvider;
import com.example.dev.moviedb.utils.DtoUtils;

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
        getActivity().setTitle("\"" + mToDetail.getPrimaryFacts().getTitle() + "\"");

        StringBuilder buffer = new StringBuilder();

        buffer.append("Title: " + mToDetail.getPrimaryFacts().getOriginalTitle());
        ((TextView) view.findViewById(R.id.details_main_title)).setText(mToDetail.getPrimaryFacts().getOriginalTitle());

        ((TextView) view.findViewById(R.id.details_description)).setText(mToDetail.getPrimaryFacts().getOverview());

        buffer.append("Release Date: " + mToDetail.getReleaseDateFacade());
        ((TextView) view.findViewById(R.id.details_release_date)).setText(mToDetail.getReleaseDateFacade());

        buffer.append("Vote Average: " + mToDetail.getPopularity().getVoteAverage());
        ((TextView) view.findViewById(R.id.details_classification)).setText(
                String.valueOf(mToDetail.getPopularity().getVoteAverage()));

        //Query for an image based upon the path.
        MovieApiProvider fetcher = ((YamdaApplication) getActivity().getApplication()).getApiFetcher();
        ImageView imgView = (ImageView) view.findViewById(R.id.details_main_picture);

        fetcher.loadImage(getActivity(), mToDetail.getMovieImages().getBackdropImagePath(),
                MovieApiProvider.DETAILS_IMG_SIZE, R.drawable.details_placeholder, imgView);

        String humanReadableRuntime = DtoUtils.transformRuntime(mToDetail.getAdvancedFacts().getRuntime());
        setupElement(view, R.id.details_runtime, R.id.details_runtime_icon, humanReadableRuntime);


        setupElement(view, R.id.details_status, R.id.details_status_icon,
                mToDetail.getAdvancedFacts().getReadableStatus(getActivity().getApplication()));

        Log.d("GENRES", mToDetail.getAdvancedFacts().getGenres().representation());
        setupElement(view, R.id.details_main_genre, NO_ICON, mToDetail.getAdvancedFacts().getGenres().representation());

        setupElement(view, R.id.tagline, NO_ICON, "\"" + mToDetail.getAdvancedFacts().getTagLine() + "\"");


        setupElement(view, R.id.imdb_textview, NO_ICON, mToDetail.getAdvancedFacts().getImdbID());

        startTrailer(view, mToDetail.getTrailer());
        Log.d(TAG, buffer.toString());
        return view;
    }
}
