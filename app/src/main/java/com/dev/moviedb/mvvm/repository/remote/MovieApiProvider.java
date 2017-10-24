package com.dev.moviedb.mvvm.repository.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.dev.moviedb.model.Movie;
import com.dev.moviedb.model.MovieAggregator;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.model.dto.MovieAggregatorDTO;
import com.dev.moviedb.model.dto.MovieDTO;
import com.dev.moviedb.model.mapper.DataMapper;
import com.dev.moviedb.network.http.provider.ImageDownloader;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
     * Size of thumbnail images.
     */
    public static final String DEFAULT_IMG_SIZE = "w154/";

    /**
     * Size of images inside the details page.
     */
    public static final String DETAILS_IMG_SIZE = "w500/";

    /**
     * The service instance that provides concrete implementation of
     * all the methods available to this api.
     */
    private TmdbApiService mService;

    /**
     * The api key used to communicate with the api.
     */
    private final String API_KEY = "571d26584c04ffbdfdb95c267be1c5a2";


    /**
     * An instance of DataMapper. Performs the conversion
     * between types from the web api and our model.
     */
    private DataMapper mMapper;


    /**
     * /**
     * Creates an instance of {@link MovieApiProvider} that provides access
     * to the api specified by {@link TmdbApiService}.
     *
     * The Retrofit client's converter used underneath is based upon Gson.
     */
    public MovieApiProvider() {
        /*
      The base url for TMDB API
     */
        final String BASE_API_URL = "https://api.themoviedb.org/3/";
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retro.create(TmdbApiService.class);

        mMapper = new DataMapper();
    }


    /**
     * Converts callbacks that use our app's model into callbacks that use DTOs.
     * The callback return follows the specification of the retrofit library.
     *
     * @param completionCallback The callback to encapsulate into another callback.
     * @see Callback
     */
    private Callback<MovieDTO> wrapMovieResponseTask(final Completion<Movie> completionCallback) {
        return new Callback<MovieDTO>() {
            @Override
            public void onResponse(Call<MovieDTO> call,Response<MovieDTO> response) {

                final CallResult<Movie> result = response.isSuccessful() ?
                        new CallResult<>(mMapper.convertFrom(response.body())) :
                        new CallResult<Movie>(new Exception(response.errorBody().toString()));

                completionCallback.onResult(result);
            }

            @Override
            public void onFailure(Call<MovieDTO> call, Throwable t) {
                completionCallback.onResult(new CallResult<Movie>(new Exception(t)));
            }
        };
    }

    /**
     * Converts callbacks that use DTOs into callbacks that use our app's model.
     * The callback return follows the specification of the retrofit library.
     *
     * @param completionCallback The callback to encapsulate into another callback.
     * @see Callback
     */
    private Callback<MovieAggregatorDTO> wrapMovieAggregatorResponseTask(final Completion<MovieAggregator> completionCallback) {
        return new Callback<MovieAggregatorDTO>() {
            @Override
            public void onResponse(Call<MovieAggregatorDTO> call,Response<MovieAggregatorDTO> response){

                final CallResult<MovieAggregator> result = response.isSuccessful() ?
                        new CallResult<>(mMapper.convertFrom(response.body())) :
                        new CallResult<MovieAggregator>(new Exception(response.errorBody().toString()));

                completionCallback.onResult(result);
            }

            @Override
            public void onFailure(Call<MovieAggregatorDTO> call, Throwable t) {
                completionCallback.onResult(new CallResult<MovieAggregator>(new Exception(t)));
            }
        };
    }


    /**
     * This function wraps the retrofit's generated api in order to query for an item by the given
     * id.
     *
     * @return An instance of {@link Call} that is capable of being canceled.
     */
    public Call<MovieDTO> findMovieByIdAsync(long id, Locale queryLang, String whatToAppend, final Completion<Movie> completionCallback) {
        final Call<MovieDTO> futureTask = mService.findMovieById(id, API_KEY, queryLang.getLanguage(), whatToAppend);
        //handle response, convert accordingly
        futureTask.enqueue(wrapMovieResponseTask(completionCallback));
        //to support cancellation
        return futureTask;
    }

    /**
     * Performs a search based upon given keywords.
     *
     * @param queryKeyWorks      The query keywords given by the user.
     * @param queryLang          The language of the response
     * @param page               The page number
     * @param completionCallback The callback to be invoked when results are available.
     * @return An instance of {@link Call} that is capable of being canceled.
     */
    public Call<MovieAggregatorDTO> searchMoviesByKeywordAsync(String queryKeyWorks, Locale queryLang, int page, final Completion<MovieAggregator> completionCallback) {
        Call<MovieAggregatorDTO> futureTask = mService.searchMoviesByKeyword(API_KEY,
                queryKeyWorks, queryLang.getLanguage(), page);
        futureTask.enqueue(wrapMovieAggregatorResponseTask(completionCallback));
        return futureTask;
    }

    /**
     * Performs a search for the most popular movies in the api.
     *
     * @param queryLang          The language of the response
     * @param completionCallback The callback to be invoked when results are available.
     * @return An instance of {@link Call} that is capable of being canceled.
     */
    public Call<MovieAggregatorDTO> findMostPopularMoviesAsync(Locale queryLang, final Completion<MovieAggregator> completionCallback) {
        return null;
    }

    /**
     * Performs a search for the movies currently in theaters.
     *
     * @param queryLang          The language of the response
     * @param completionCallback The callback to be invoked when results are available.
     * @return An instance of {@link Call} that is capable of being canceled.
     */
    public Call<MovieAggregatorDTO> findInTheatersMoviesAsync(Locale queryLang, final Completion<MovieAggregator> completionCallback) {
        Call<MovieAggregatorDTO> futureTask = mService.findNowPlayingMovies(API_KEY,
                queryLang.getLanguage());
        futureTask.enqueue(wrapMovieAggregatorResponseTask(completionCallback));
        return futureTask;
    }


    /**
     * Performs a search for the movies currently in theaters.
     *
     * @param queryLang          The language of the response
     * @param page               The page number
     * @param completionCallback The callback to be invoked when results are available.
     * @return An instance of {@link Call} that is capable of being canceled.
     */
    public Call<MovieAggregatorDTO> findUpcomingMoviesAsync(Locale queryLang, final Completion<MovieAggregator> completionCallback, int page) {
        Call<MovieAggregatorDTO> futureTask = mService.findUpcomingMovies(API_KEY, queryLang.getLanguage(), page);
        futureTask.enqueue(wrapMovieAggregatorResponseTask(completionCallback));
        return futureTask;
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

        String completeUrlImage = buildImageUrl(imageUrlPath, imageSize);
        ImageDownloader futureTask = new ImageDownloader(placeholder);
        futureTask.download(completeUrlImage, view);
        return futureTask;
    }

    /**
     * Same as downloadImage but makes use of Picasso library.
     */
    public void loadImage(Context ctx, String imgRelativePath, String imgSize, int placeholder, ImageView imgView) {
        //Picasso.with(ctx).setIndicatorsEnabled(true);
        Picasso.with(ctx)
                .load(buildImageUrl(imgRelativePath, imgSize))
                .placeholder(placeholder)
                .into(imgView);
    }

    /**
     * Build an image url according with the specification provided by the web api.
     *
     * @param imgRelativePath The relative path of the url
     * @param imageSize       Size required for the image
     * @return An image url
     * @see "www.themoviedb.org/documentation/api"
     */
    private String buildImageUrl(String imgRelativePath, String imageSize) {
        /*
      All the images have this base url according to the TMDB API.
     */
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
        return BASE_IMAGE_URL.concat(imageSize).concat(imgRelativePath);
    }
}

