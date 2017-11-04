package com.example.dev.moviedb.rest;

public class MovieApiFetcherTest {


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

    private Callback<MovieCollectionDto> goodCallback(final String msg){
        return new Callback<MovieCollectionDto>() {
            @Override
            public void onResponse(Response<MovieCollectionDto> response, Retrofit retrofit) {
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

    private Callback<MovieCollectionDto> badCallback(final String msg){
        return new Callback<MovieCollectionDto>() {
            @Override
            public void onResponse(Response<MovieCollectionDto> response, Retrofit retrofit) {
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
