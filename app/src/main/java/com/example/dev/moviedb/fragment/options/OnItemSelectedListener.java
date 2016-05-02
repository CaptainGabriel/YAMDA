package com.example.dev.moviedb.fragment.options;

/**
 * Implement this interface in order to receive events that
 * tell an item was clicked by the user.
 * Made to be used between fragments and their host activities.
 *
 * @version 1.0.0
 */
public interface OnItemSelectedListener<T> {
    /**
     * Called by the fragment when a list item is selected.
     *
     * @param table The Database table where the record resides
     * @param obj   The selected object
     */
    void onItemSelected(String table, T obj);

    /**
     * Called by the fragment when a list item is selected.
     *
     * @param obj
     *  The selected object.
     */
    void onItemSelected(T obj);
}