package com.dev.moviedb.mvvm.movieDetails

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.dev.moviedb.mvvm.extensions.*
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import com.dev.moviedb.mvvm.repository.remote.dto.GenreDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import com.dev.moviedb.mvvm.repository.remote.dto.ResultImageDTO
import kotlinx.android.synthetic.main.item_movie_detail_layout.*
import kotlinx.android.synthetic.main.toolbar_center_text.*
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


        val recyclerView = findViewById<RecyclerView>(R.id.images_list)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.adapter = ImageListAdapter()
        recyclerView?.itemAnimator = DefaultItemAnimator()

        toolbar.title = ""
        setSupportActionBar(toolbar)
        toolbar_title.text = ""

        //provide up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.extras[ITEM_ARGS_KEY] as MovieDTO

        (recyclerView?.adapter as ImageListAdapter).addNewData(movie.images?.backdrops?.map { result -> result.filePath } as ArrayList<String>?)
        recyclerView.adapter.notifyDataSetChanged()

        provideDataToLayout(movie)

        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(9,9,9,9)

        handleGenreTags(movie, params)

        handleCastingPhotos(movie)

        movie.images?.backdrops?.forEach { backdrop: ResultImageDTO ->
            run {

                val imageView = ImageView(this)
                imageView.loadBackdropUrl(backdrop.filePath, ApiConsts.POSTER_SMALL_IMG_SIZE)
                images_list.addView(imageView)
            }
        }
    }

    private fun handleCastingPhotos(movie: MovieDTO) {
        movie.credits?.cast?.let { castingCrew ->
            run {
                (0..4).forEach { i ->
                    run {
                        val castPhoto = ImageView(this)
                        castPhoto.setOnClickListener({ _ -> Toast.makeText(this, castingCrew[i].name, Toast.LENGTH_SHORT).show() })
                        castPhoto.setPadding(3, 0, 7, 0)
                        castingCrew[i].profile_path?.let { castPhoto.loadRoundedPhoto(this, it) }
                        casting_images_container.addView(castPhoto)
                    }
                }
            }
        }
    }

    private fun handleGenreTags(movie: MovieDTO, params: LinearLayout.LayoutParams) {
        movie.genres?.forEach { genreDTO: GenreDTO ->
            run {

                val category = TextView(this)
                category.text = genreDTO.name
                category.background = resources.getDrawable(R.drawable.round_shape)
                category.gravity = Gravity.CENTER
                category.setPadding(3, 3, 3, 3)
                category.setTextColor(Color.WHITE)
                category.textSize = 11F
                category.layoutParams = params
                movie_categories_container.addView(category)
            }
        }
    }

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
        movie_release_status.text = movie.status
        status_label.text = movie.releaseDate.getYear()
    }


}
