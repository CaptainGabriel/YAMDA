
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.dev.moviedb.mvvm.repository.local.db.AppDatabase
import com.dev.moviedb.mvvm.repository.local.db.entity.Movie
import com.dev.moviedb.mvvm.repository.local.db.entity.NowPlayingMovie
import com.dev.moviedb.mvvm.repository.local.db.entity.PopularMovie
import com.dev.moviedb.mvvm.repository.local.db.entity.TopRatedMovie
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 *
 *
 * @author PeteGabriel on 05/11/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
open class PopularDaoTest(){

    lateinit var mDatabase : AppDatabase

    @Before
    fun initDB(){
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),AppDatabase::class.java).build()
    }

    @Test
    fun testInsertionInPopularDao(){

        val popularMovie = PopularMovie(Movie(id = 315635))
        mDatabase.popularDao().insert(popularMovie, {})

        assert(!mDatabase.popularDao().fetchAll().isEmpty())
    }


    @Test
    fun testInsertionInTopRatedDao(){

        val topRatedMovie = TopRatedMovie(Movie(id = 315635))
        mDatabase.topRatedDao().insert(topRatedMovie, {})

        assert(!mDatabase.topRatedDao().fetchAll().isEmpty())
    }

    @Test
    fun testInsertionInNowPlayingDao(){

        val playingMovie = NowPlayingMovie(Movie(id = 315635))
        mDatabase.nowPlayingDao().insert(playingMovie, {})

        assert(!mDatabase.nowPlayingDao().fetchAll().isEmpty())
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }


}