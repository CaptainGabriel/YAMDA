package com.dev.moviedb.mvvm.repository.local.db.entity.content;

/**
 * An {@link Identifiable} type must be able to give an
 * id that identifies itself;
 *
 * Yamda 1.0.0
 */
interface Identifiable {

    /**
     * Return an id that uniquely identifies the instance.
     * @return
     */
    fun resolveID(): Long
}
