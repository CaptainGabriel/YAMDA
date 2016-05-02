package com.example.dev.moviedb.model.async;

/**
 * The contract to be supported to receive completion notifications
 * of asynchronous operations.
 *
 * @author Paulo Pereira
 */
public interface Completion<T> {

    /**
     * The callback invoked when the result is available.
     * @param result
     *      The result passed to the callback.
     */
    void onResult(CallResult<T> result);
}
