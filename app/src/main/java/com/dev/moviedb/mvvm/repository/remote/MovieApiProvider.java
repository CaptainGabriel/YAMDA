package com.dev.moviedb.mvvm.repository.remote;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.dev.moviedb.network.http.provider.ImageDownloader;


/**
 * This class represents a specific implementation of the interface {@link TmdbApiService}.
 *
 * An instance of this class provides access to the api specified by
 * that very same interface.
 *
 * @version 0.0.1
 * @see TmdbApiService
 */
public class MovieApiProvider {


    /**
     * /**
     * Creates an instance of {@link MovieApiProvider} that provides access
     * to the api specified by {@link TmdbApiService}.
     *
     * The Retrofit client's converter used underneath is based upon Gson.
     */
    public MovieApiProvider() {

    }



    /**
     * Asynchronous method that downloads an image based on the url given and
     * sets it into the given view.
     *
     * @param imageUrlPath The poster relative path where the image resides
     * @param view         The view where the image must be visible
     * @return An instance of {@link ImageDownloader} that represents a task that will conclude
     * in the future and so offers the option of being canceled.
     */
    @Deprecated
    public ImageDownloader downloadImage(String imageUrlPath, ImageView view, String imageSize, Bitmap placeholder) {
        if (imageUrlPath == null || imageUrlPath.isEmpty()) {
            return null;
        }

        String completeUrlImage = "";
        ImageDownloader futureTask = new ImageDownloader(placeholder);
        futureTask.download(completeUrlImage, view);
        return futureTask;
    }


}

