package com.dev.moviedb.mvvm.movieDetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.moviedb.mvvm.extensions.loadBackdropUrl
import com.dev.moviedb.mvvm.extensions.loadPosterUrl
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import kotlinx.android.synthetic.main.item_movie_detail_layout.*
import petegabriel.com.yamda.R

/**
 * This activity shows the details of a certain movie passed
 * as parameter.
 *
 * Yamda 1.0.0
 */
class MovieDetailsActivity : AppCompatActivity() {

    /**
     * Use this key to pass a certain movie inside a bundle.
     */
    companion object {

        val ITEM_ARGS_KEY = "movie_item_argument"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_movie_detail_layout)

        var movie = intent.extras[ITEM_ARGS_KEY] as MovieDTO
        provideDataToLayout(movie)
    }


    private fun provideDataToLayout(movie: MovieDTO) {
        movie.backdropPath?.let { backdrop_movie_img.loadBackdropUrl(it, false) }
        movie.posterPath?.let { poster_movie_img.loadPosterUrl(it) }
        movie.title?.let { movie_name.text = it }
        storyline_content.text = movie.overview
        rating_score?.text = "%.1f".format(movie.voteAverage)
    }


}
