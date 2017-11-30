package repository

import com.dev.moviedb.mvvm.repository.PopularMovieRepository
import com.dev.moviedb.mvvm.repository.local.db.dao.PopularDao
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 *
 * Yamda 1.0.0.
 */

class PopularMovieRepositoryTest {

    lateinit var popularMovRepo: PopularMovieRepository
    lateinit var popularDao: PopularDao


    @Before
    fun setup() {
        popularDao = mock(PopularDao::class.java)
        `when`(popularDao.fetchAll()).thenReturn(Single.just(emptyList()))
        popularMovRepo = PopularMovieRepository(mock(TmdbApiService::class.java), popularDao)
    }

    @Test
    fun test_emptyCache_noDataOnApi_returnsEmptyList() {
        `when`(popularDao.fetchAll()).thenReturn(Single.just(emptyList()))

        popularMovRepo.findAll().test()
                .assertValue { it.isEmpty() }
    }

}