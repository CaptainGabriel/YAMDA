package rest;

import android.support.annotation.NonNull;

import com.example.ricardopeixe.moviedb.model.async.CallResult;
import com.example.ricardopeixe.moviedb.model.async.Completion;
import com.example.ricardopeixe.moviedb.model.dto.MovieDTO;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.network.http.provider.MovieApiProvider;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Call;

/**
 * Unit tests for the MovieApiFetcher class.
 *
 * Contains async and sync versions of the retrofit library.
 *
 */
public class MovieDbFetcherTest {

    private MovieApiProvider apiFetcher;
    private List<Movie> helperList;

    @Before
    public void createResources(){
        apiFetcher = new MovieApiProvider();
        helperList = new ArrayList<>();
    }
/*
    @Test
    public void findPopularMoviesAsync() throws IOException, InterruptedException {
        apiFetcher.findMostPopularMoviesAsync(Locale.ENGLISH, (Completion<MovieAggregator>) goodCallback("some msg", new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {

            }
        }));
        Thread.currentThread().join(2000);
    }



    @Test
    public void findUpcomingMoviesAsync() throws IOException, InterruptedException {
        apiFetcher.findUpcomingMoviesAsync(Locale.ENGLISH, (Completion<MovieAggregator>) goodCallback("some msg", new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {

            }
        }), 1);
        Thread.currentThread().join(2000);
    }



    @Test
    public void testFindNowPlayingMoviesAsync() throws IOException, InterruptedException {
        apiFetcher.findInTheatersMoviesAsync(Locale.ENGLISH, (Completion<MovieAggregator>) goodCallback("some msg", new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {

            }
        }));
        Thread.currentThread().join(2000);
    }



    private Callback<MovieAggregator> goodCallback(final String msg, Completion<MovieAggregator> cb){
        return new Callback<MovieAggregator>() {
            @Override
            public void onResponse(Response<MovieAggregator> response, Retrofit retrofit) {
                Assert.assertNotNull(response.body());
                helperList.clear();
                for (Movie m : response.body().getmResults())
                    helperList.add(m);

                Assert.assertFalse(helperList.isEmpty());
            }

            @Override
            public void onFailure(Throwable t) {}
        };
    }
*/


    @Test
    public void testFindMovieByIDAsync() throws IOException, InterruptedException {
        Call<MovieDTO> c = apiFetcher.findMovieByIdAsync(135397, Locale.ENGLISH, cb());
        Thread.currentThread().join(200000);
    }

    private Completion<Movie> cb(){
        return new Completion<Movie>() {
            @Override
            public void onResult(@NonNull CallResult<Movie> result) {
                Movie movie = null;
                try {
                    movie = result.getResult();
                    Assert.assertEquals("Jurassic World", movie.getPrimaryFacts().getTitle());
                    Assert.assertTrue(135397 == movie.getPrimaryFacts().getId());
                    Assert.assertEquals("jjBgi2r5cRt36xF6iNUEhzscEcb.jpg", movie.getMovieImages().getPosterImagePath());
                    Assert.assertEquals("dkMD5qlogeRMiEixC4YNPUvax2T.jpg", movie.getMovieImages().getBackdropImagePath());
                    Assert.assertEquals(124, movie.getAdvancedFacts().getRuntime());
                    Assert.assertEquals("2015-06-12", movie.getReleaseDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
