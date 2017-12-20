package ui.moviesTab

import android.widget.TextView
import com.dev.moviedb.mvvm.activities.TabOptionsActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import petegabriel.com.yamda.R


/**
 *
 * Yamda 1.0.0.
 */
@RunWith(RobolectricTestRunner::class)
class MoviesTabTest {

    @Test
    fun navigatingToMoviesTab_shouldHaveAppTitleInToolbar() {
        val activity = Robolectric.setupActivity(TabOptionsActivity::class.java)
        val toolbarTitleText = activity.findViewById<TextView>(R.id.toolbar_title).text

        assert("YAMDA" == toolbarTitleText)
    }

}