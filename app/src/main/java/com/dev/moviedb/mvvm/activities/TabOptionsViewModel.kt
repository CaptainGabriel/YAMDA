package com.dev.moviedb.mvvm.activities

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MultiDataDTO
import io.reactivex.Single

class TabOptionsViewModel constructor(private var apiService: TmdbApiService) {

    fun query(query: String): Single<MultiDataDTO> {
        return apiService.searchData(query=query)
    }

}