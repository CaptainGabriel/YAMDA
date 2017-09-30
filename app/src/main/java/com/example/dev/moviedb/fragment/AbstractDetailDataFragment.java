package com.example.dev.moviedb.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dev.moviedb.utils.ToastUtils;

import petegabriel.com.yamda.R;

/**
 * This class represents the common behavior between fragments that display details
 * of a certain type.
 *
 * @version 1.0.0
 */
public abstract class AbstractDetailDataFragment<T extends Parcelable> extends LifecycleLoggingFragment {

    /**
     * This constant acts as a key that helps building an instance of this class
     * when other classes want to pass parameters to it.
     */
    protected static final String SAVED_PARCELABLE_KEY = "DetailFragment_Parcelable_Item_key";

    /**
     * The item from which details are taken and shown.
     */
    protected T mToDetail;

    /** {@inheritDoc} */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //if mToDetail is equal to null, check sources. Otherwise retain fragment engine deals with it.
        if (null != savedInstanceState && mToDetail == null) {
            //when state was saved
            mToDetail = savedInstanceState.getParcelable(SAVED_PARCELABLE_KEY);
        } else //when comes from another activity
        {
            mToDetail = getArguments().getParcelable(SAVED_PARCELABLE_KEY);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_PARCELABLE_KEY, mToDetail);
    }

    /**
     * Means no widget to setup.
     */
    protected final int NO_WIDGET = -2;

    /**
     * Means no icon to setup.
     */
    protected final int NO_ICON = -1;


    protected void setupTextWidget(View inflatedView, int widgetId, String content) {
        if (widgetId != NO_WIDGET) {
            TextView imdbTextView = ((TextView) inflatedView.findViewById(widgetId));
            imdbTextView.setVisibility(View.VISIBLE);
            imdbTextView.setText(content);
        }
    }

    protected void setupIconWidget(View inflatedView, int iconId) {
        if (iconId != NO_ICON) {
            inflatedView.findViewById(iconId).setVisibility(View.VISIBLE);
        }
    }


    /**
     * For a pair of widgets, it enables them and assigns content.
     *
     * @param view    The inflated view
     * @param elemId  The text element's id
     * @param iconId  The icon's id
     * @param content Text element's content
     */
    protected void setupElement(View view, int elemId, int iconId, String content) {
        setupTextWidget(view, elemId, content);
        setupIconWidget(view, iconId);
    }

    /**
     * This method applies a click listener to the fabelement inside the layout that shows the
     * details
     * of a certain movie.
     * When applying the listener, at the same time, we know if there will be a link to display or
     * not.
     * If not the icon will be disabled. Otherwise, will be possible to click on it.
     *
     * @param inflatedLayout The inflated layout from where to get a reference to the fab.
     * @param trailerUrl     The url to the movie.
     */
    void startTrailer(final View inflatedLayout, final String trailerUrl) {
        final FloatingActionButton trailerFab = (FloatingActionButton) inflatedLayout.findViewById(R.id.youtubeStartPlaying);
        if (trailerUrl == null) {
            //disable and gray out the icon
            trailerFab.setEnabled(false);
            trailerFab.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else {
            trailerFab.setEnabled(true);
            int color = getResources().getColor(R.color.colorAccent);
            trailerFab.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        trailerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "The trailer fab just got clicked!");
                if (trailerUrl != null) {
                    //Implicit Intent + ActionView = "I want to display something based upon this intent. figure it out"
                    Log.d(TAG, "URL of the video: " + trailerUrl);
                    Intent videoClient = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    startActivity(videoClient);
                } else {
                    Log.d(TAG, "No trailer to display");
                    ToastUtils.showError("No Trailer to display", getActivity());
                }
            }
        });
    }
}
