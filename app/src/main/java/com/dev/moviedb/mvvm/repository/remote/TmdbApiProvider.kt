package com.dev.moviedb.mvvm.repository.remote

import petegabriel.com.yamda.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class wraps an implementation of the operations specified in the
 * interface {@link TmdbApiService}.
 *
 * @author PeteGabriel on 13/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
class TmdbApiProvider {

    private val service: TmdbApiService

    init {
        val retroInstance = Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retroInstance.create(TmdbApiService::class.java)
    }


    fun getTmdbApiService(): TmdbApiService = service

}