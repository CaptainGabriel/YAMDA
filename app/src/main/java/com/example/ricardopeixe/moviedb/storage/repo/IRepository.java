package com.example.ricardopeixe.moviedb.storage.repo;

import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.model.async.Completion;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataRecord;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;

import java.util.List;

/**
 * This interface represents the protocol that classes must use in order
 * to access and/or store data in the application.
 *
 * The methods "getUpcomingMovies" and "getInTheatersMovies" are the ones that
 * can be called by other classes in order to obtain information. In order to store date inside
 * the repository, classes can call the "storeListOfUpcomingMovies" and "storeListInTheatersMovies"
 * methods.
 *
 * @version 0.0.2
 */
public interface IRepository {


    void getFavoriteRecords(Completion<List<DataRecord>> completion);

    /**
     * Try to insert a set of movies into de local storage.
     *
     * @param resourceUri Uri that identifies the resource.
     * @param setOfMovies Set of items to insert.
     */
    void insertSetOfMovies(final Uri resourceUri, final List<Movie> setOfMovies);

    /**
     * Retrieve a set of records from the local storage.
     *
     * @param resourceUri Uri that identifies the resource.
     * @param onResult    The callback to be invoked after the results are available.
     */
    void getSetOfRecords(final Uri resourceUri, final Completion<List<DataRecord>> onResult);


    /**
     * Retrieve a specific record according with the given id.
     *
     * @param resourceUri            Uri that identifies the resource.
     * @param id                     The id of the record
     * @param callbackToHandleResult The callback to be invoked after the results are available.
     */
    void getRecordByID(final Uri resourceUri, long id, Completion<DataRecord> callbackToHandleResult);


    /**
     * Search for a movie marked as favorite with the given id.
     *
     * @param id                     The movie's id
     * @param callbackToHandleResult The callback to be executed when the result is available
     */
    void getFavoriteRecordByID(long id, Completion<DataRecord> callbackToHandleResult);

    /**
     * Invoke to update data from an already existing item in the local storage
     * with data coming from the instance given as parameter.
     * Only the dynamic part of the item's info is updated.
     *
     * @param item The instance that contains the updated information
     */
    void updateRecord(Movie item);


    /**
     * Update the status of favorite collumn for a given record inside a certain table.
     *
     * @param app         The application's context.
     * @param targetTable The target table where the record is expected to reside.
     * @param record      The record
     */
    void updateFavoriteStatus(Context app, String targetTable, DataRecord record);


    /**
     * Delete the record identified by the given uri.
     *
     * @param resourceUri Uri that identifies the resource.
     * @param record      DataRecord instance to delete.
     * @return Amount of affected rows.
     */
    void deleteRecord(final Uri resourceUri, DataRecord record, Completion<Void> cb);

    /**
     * Clear the entire local storage.
     */
    void clearData();

    /**
     * Forces the fetch of data from the web.
     *
     */
    void forceFetchOfNewData(Context ctx);

    /**
     * Schedule the services with a certain time interval.
     */
    void scheduleServices(Application ctx, long interval);

    /**
     * Reschedule the services
     */
    void rescheduleServices(AlarmManager alarmManager, long interval);


    /**
     * Insert the given record into the target table.
     *
     * @param targetTable The database table
     * @param record      The record to insert
     */
    void insertRecord(String targetTable, DataRecord record);
}