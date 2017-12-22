package com.dev.moviedb.mvvm.seriesTab


import android.content.Intent
import android.os.Bundle
import com.dev.moviedb.YamdaApplication
import com.dev.moviedb.mvvm.fragments.AbstractDisplayFragment
import com.dev.moviedb.mvvm.movieDetails.MovieDetailsActivity
import com.dev.moviedb.mvvm.repository.TvShowRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import petegabriel.com.yamda.R


/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0
 */
class SeriesTabFragment : AbstractDisplayFragment() {

    override fun getLoggingTag(): String = this.javaClass.canonicalName

    /**
     * A reference to the view model class
     */
    private var viewModel: SeriesTabViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = activity.applicationContext as YamdaApplication
        val tvShowRepo = TvShowRepository(app.apiService)
        viewModel =  SeriesTabViewModel(tvShowRepo)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //airing today tv shows in first adapter
        viewModel?.findAiringTodayTvSeries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( { t -> addNewDataToFirstAdapter()(ArrayList(t.results)) },
                        { throwable -> handleError(throwable) })

        //most popular tv shows
        viewModel?.findPopularTvSeries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( { t -> addNewDataToSecondAdapter()(ArrayList(t.results)) },
                             { throwable -> handleError(throwable) })

        //top rated tv shows
        viewModel?.findTopRatedTvSeries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t -> addNewDataToThirdAdapter()(ArrayList(t.results)) },
                        { throwable -> handleError(throwable) })



    }

    override fun handleItemClick(): (MovieDTO) -> Unit {
        return { m ->
            run {
                viewModel?.findTvShowById(m.id.toLong(), "credits,videos,images")
                        ?.subscribeOn(Schedulers.newThread())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ t ->
                            run {
                                navigateToDetailsVIew(t)
                            }
                        }, { throwable ->
                            handleError(throwable)
                        })
            }
        }
    }

    private fun navigateToDetailsVIew(t: MovieDTO?) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        val b = Bundle()
        b.putParcelable(MovieDetailsActivity.ITEM_ARGS_KEY, t)
        intent.putExtras(b) //Put your id to your next Intent
        startActivity(intent)
        //set the animation of the exiting and entering Activities
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun getFirstCardTitle(): String = "Airing Today"

    override fun getSecondCardTitle(): String = getString(R.string.popular_card_title)

    override fun getThirdCardTitle(): String = getString(R.string.toprated_card_title)

    override fun showSpotlightWidget(): Boolean = false

}
