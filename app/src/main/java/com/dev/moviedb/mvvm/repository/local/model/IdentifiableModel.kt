package com.dev.moviedb.mvvm.repository.local.model

/**
 * Force every item to be identified by an ID.
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
interface IdentifiableModel {


    fun getId(): Long

}