package com.dev.moviedb.mvvm.movieDetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import com.dev.moviedb.mvvm.extensions.formatMovieRuntime
import com.dev.moviedb.mvvm.extensions.getExtendedDate
import com.dev.moviedb.mvvm.extensions.loadBackdropUrl
import com.dev.moviedb.mvvm.extensions.loadPosterUrl
import com.dev.moviedb.mvvm.nowPlayingMoviesTab.VideoPlayerFragment
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

    private var castingImagesRecyclerView: RecyclerView? = null
    private var castingImagesAdapter: RecyclerView.Adapter<*>? = null
    private var castingImagesLayoutManager: RecyclerView.LayoutManager? = null

    private var backdropImagesRecyclerView: RecyclerView? = null
    private var backdropImagesAdapter: RecyclerView.Adapter<*>? = null
    private var backdropImagesLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_movie_detail_layout)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        //toolbar_title.text = ""

        //provide up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.extras[ITEM_ARGS_KEY] as MovieDTO
        toolbar.title = if (movie.title?.isEmpty()!!) movie.name else movie.title

        provideDataToLayout(movie)

        genre_description_content.text = formatGenreTags(movie)

        //TODO handleThis release date Date when its empty
        with(movie.releaseDate){
            release_date.text = if (this.isEmpty()) "tba" else this.getExtendedDate()
        }

        setupCastingCrewImageList(movie)

        setupImagesList(movie)

        if (movie.videos.results.isNotEmpty()) {
            movieTrailerFab.setOnClickListener({ view ->
                run {
                    movie.videos.let {
                        val manager = supportFragmentManager
                        val videoPlayerFragDialog = VideoPlayerFragment.newInstance(it.results[0].key)
                        videoPlayerFragDialog.show(manager, "VideoPlayerFragDialog")
                    }
                }
            })
        }else{
            movieTrailerFab.visibility = GONE
        }

    }

    private fun setupImagesList(movie: MovieDTO) {
        if (movie.images?.backdrops?.size!! > 0) {
            backdropImagesRecyclerView = findViewById<RecyclerView>(R.id.backdrop_images_container)
            backdropImagesRecyclerView?.isHorizontalScrollBarEnabled = false
            backdropImagesRecyclerView?.setHasFixedSize(true)

            // use a linear layout manager
            backdropImagesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            backdropImagesRecyclerView?.layoutManager = backdropImagesLayoutManager

            // specify an castingImagesAdapter (see also next example)
            backdropImagesAdapter = BackdropImageListAdapter(movie.images?.backdrops!!)
            backdropImagesRecyclerView?.adapter = backdropImagesAdapter
        }else{
            textView2.visibility = GONE
        }
    }

    private fun setupCastingCrewImageList(movie: MovieDTO) {
        if (movie.credits?.cast?.size!! > 0) {
            castingImagesRecyclerView = findViewById<RecyclerView>(R.id.casting_images_container)
            castingImagesRecyclerView?.isHorizontalScrollBarEnabled = false
            castingImagesRecyclerView?.setHasFixedSize(true)

            // use a linear layout manager
            castingImagesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            castingImagesRecyclerView?.layoutManager = castingImagesLayoutManager

            // specify an castingImagesAdapter (see also next example)
            val limit = if (movie.credits?.cast?.size!! < 9) movie.credits?.cast?.size!! else 9
            castingImagesAdapter = CastingImageListAdapter(movie.credits?.cast!!.copyOfRange(0, limit))
            castingImagesRecyclerView?.adapter = castingImagesAdapter
        }else{
            textView.visibility = GONE
        }
    }

   private fun formatGenreTags(movie: MovieDTO) =
           movie.genres
                   ?.map { genre -> genre.name }
                   ?.fold("", {acc: String, next: String -> if (acc.isEmpty()) acc + next else acc + " | " + next })


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    private fun provideDataToLayout(movie: MovieDTO) {
        movie.backdropPath?.let { backdrop_movie_img.loadBackdropUrl(it, false) }
        movie.posterPath?.let { poster_movie_img.loadPosterUrl(it) }
        movie.title?.let { movie_name.text = it }
        storyline_content.text = movie.overview
        rating_score?.text = "%.1f".format(movie.voteAverage)
        runtime_length.text = movie.runtime.formatMovieRuntime()
    }


}
