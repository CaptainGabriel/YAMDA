package com.example.dev.moviedb.model;

/**
 * An {@link Identifiable} type must be able to give an
 * id that identifies itself;
 *
 * @version 1.0.0
 */
public interface Identifiable {

    /**
     * Return an id that uniquely identifies the instance.
     * @return
     */
    long resolveID();
}
