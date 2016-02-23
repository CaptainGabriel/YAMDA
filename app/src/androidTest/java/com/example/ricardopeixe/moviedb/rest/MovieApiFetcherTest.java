package com.example.ricardopeixe.moviedb.rest;

import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.network.http.apiservice.MovieApiService;
import com.example.ricardopeixe.moviedb.network.http.provider.MovieApiProvider;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;


/**
 * Unit tests for the {@link MovieApiProvider} class.
 *
 * Contains async and sync versions of the retrofit library.
 *
 * @see MovieApiProvider
 * @see MovieApiService
 */
public class MovieApiFetcherTest {

    private MovieApiProvider apiFetcher;
    private List<Movie> adapter;

    @Before
    public void createResources(){
        apiFetcher = new MovieApiProvider();
        adapter = new ArrayList<>();
    }

/*

    @Test
    public void testFindPopularMoviesAsync() throws IOException {
        apiFetcher.findMostPopularMoviesAsync(Locale.ENGLISH, goodCallback("some msg"));
    }

    @Test
    public void findPopularMoviesAsyncWrongApiKey() throws IOException {
        apiFetcher.findMostPopularMovies(badCallback("some msg"));
    }

    @Test
    public void findUpcomingMoviesAsync() throws IOException {
        apiFetcher.findUpcomingMovies(goodCallback("some msg"));
    }

    @Test
    public void findUpcomingMoviesAsyncWrongApiKey() throws IOException {
        apiFetcher.findUpcomingMovies(badCallback("some msg"));
    }

    @Test
    public void findNowPlayingMoviesAsync() throws IOException {
        apiFetcher.findNowPlayingMovies(goodCallback("some msg"));
    }

    @Test
    public void findNowPlayingMoviesAsyncWrongApiKey() throws IOException {
        apiFetcher.findUpcomingMovies(badCallback("some msg"));
    }

    private Callback<MovieAggregatorDTO> goodCallback(final String msg){
        return new Callback<MovieAggregatorDTO>() {
            @Override
            public void onResponse(Response<MovieAggregatorDTO> response, Retrofit retrofit) {
                Assert.assertNotNull(response.body());
                adapter.clear();
                for (MovieDTO m : response.body().results)
                    adapter.add(m);

                Assert.assertFalse(adapter.isEmpty());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }

    private Callback<MovieAggregatorDTO> badCallback(final String msg){
        return new Callback<MovieAggregatorDTO>() {
            @Override
            public void onResponse(Response<MovieAggregatorDTO> response, Retrofit retrofit) {
                Assert.assertFalse(response.isSuccess());
            }

            @Override
            public void onFailure(Throwable t) { }
        };
    }

    @Test
    public void findMovieByIDAsync() throws IOException {
        apiFetcher.findMovieById(135397, idCb("something"));
    }

    private Callback<MovieDTO> idCb(final String msg){
        return new Callback<MovieDTO>() {
            @Override
            public void onResponse(Response<MovieDTO> response, Retrofit retrofit) {
                Assert.assertEquals("Jurassic World", response.body().getTitle());
                Assert.assertEquals(135397, response.body().getId());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }
*/

}
