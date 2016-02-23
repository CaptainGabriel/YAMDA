package com.example.ricardopeixe.moviedb.storage.repo;

import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.model.async.CallResult;
import com.example.ricardopeixe.moviedb.model.async.Completion;
import com.example.ricardopeixe.moviedb.service.FetchInTheatersMoviesService;
import com.example.ricardopeixe.moviedb.service.FetchPopularMoviesService;
import com.example.ricardopeixe.moviedb.service.FetchUpcomingMoviesService;
import com.example.ricardopeixe.moviedb.service.NotificationReleaseService;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataProvider;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataRecord;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataUnit;
import com.example.ricardopeixe.moviedb.utils.RepoUtils;
import com.example.ricardopeixe.moviedb.utils.ServicesUtils;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The concrete implementation of a local repository that follows the guidelines
 * specified by the interface {@link IRepository}.
 *
 * @version 0.0.2
 */
public class Repository implements IRepository {

    /**
     * According with the options set by the user, the app might need to rescheduled
     * some services. To reschedule we need their reference. This list provides just that.
     */
    private List<PendingIntent> pendingIntentList;

    /**
     * Reference to the application context.
     */
    private YamdaApplication mApp;

    /**
     * The reference for the implementation of AsyncQueryHandler
     */
    private RepoQueryHandler mQueryHandler;

    /**
     * Helper method that helps with building an uri to a certain resource.
     *
     * @param tableTarget A table constant from {@link DataProvider}
     * @return Uri instance
     */
    public static Uri makeUri(String tableTarget) {
        return Uri.parse("content://" + DataProvider.AUTHORITY + RepoUtils.translateTable(tableTarget));
    }


    /*
    Passar "application" é menos mau do que passar Context porque uma instancia
    de Application existe sempre
    enquanto que de context nao tenho essa garantia.

    Ao segurar uma ref para Context, posso estar a impossibilitar a recolha
    de outros objectos pelo GC.
     */
    public Repository(Application app) {
        this.mApp = (YamdaApplication) app;

        pendingIntentList = new ArrayList<>();

        mQueryHandler = new RepoQueryHandler(mApp.getContentResolver());
    }


    @Override
    public synchronized void getSetOfRecords(final Uri resourceUri, final Completion<List<DataRecord>> onResult) {
        Cursor cursor = mApp.getContentResolver().query(resourceUri, null, null, null, null);
        List<DataRecord> records = new ArrayList<>();

        if (cursor == null) {
            onResult.onResult(new CallResult<List<DataRecord>>(new Exception("Lista Vazia")));
            return;
        }

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DataRecord record = RepoUtils.getDataRecord(cursor);
                records.add(record);
                cursor.moveToNext();
            }
            cursor.close();
            onResult.onResult(new CallResult<>(records));
        }else{
            cursor.close();
            onResult.onResult(new CallResult<List<DataRecord>>(new Exception("Lista Vazia")));
        }
    }


    @Override
    public synchronized void getFavoriteRecords(Completion completion) {
        List<DataRecord> dataRecordList = new ArrayList<>();
        //segundo parametro a null significa uma projecção de todas as colunas.
        Uri allResources = Repository.makeUri(DataProvider.FAVORITE_TABLE_NAME);
        Cursor cursor = mApp.getContentResolver().query(allResources, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                DataRecord record = RepoUtils.getFavoriteRecord(cursor);
                dataRecordList.add(record);
                cursor.moveToNext();
            }
            cursor.close();
            Log.d("getFavRecords", "Found " + dataRecordList.size() + " favorite items.");
            completion.onResult(new CallResult<>(dataRecordList));
        } else {
            completion.onResult(new CallResult<>(new Exception("ResultSet is empty")));
        }
    }

    @Override
    public synchronized void getRecordByID(final Uri resourceUri, final long idOfMovie, final Completion callback) {

        mQueryHandler.startQuery(RepoQueryHandler.HANDLER_QUERY,
                callback,
                resourceUri,
                new String[]{
                        DataUnit.ID, DataUnit.TITLE, DataUnit.RELEASE_DATE, DataUnit.OVERVIEW,
                        DataUnit.BACKDROP_IMAGE, DataUnit.POSTER_IMAGE, DataUnit.VOTE_AVERAGE,
                        DataUnit.IS_FAVORITE, DataUnit.CREATED_AT, DataUnit.IMDB_ID,
                        DataUnit.RUNTIME, DataUnit.STATUS, DataUnit.TAGLINE, DataUnit.GENRES,
                        DataUnit.TRAILER_URL
                },
                DataUnit.ID + " =?",
                new String[]{String.valueOf(idOfMovie)},
                null);
    }

    @Override
    public void getFavoriteRecordByID(long id, Completion callback) {
        Uri resourceUri = makeUri(DataProvider.FAVORITE_TABLE_NAME);
        resourceUri = ContentUris.withAppendedId(resourceUri, id);
        mQueryHandler.startQuery(RepoQueryHandler.HANDLER_FAVORITE_QUERY,
                callback,
                resourceUri,
                new String[]{
                        DataUnit.ID, DataUnit.TITLE, DataUnit.RELEASE_DATE, DataUnit.OVERVIEW,
                        DataUnit.BACKDROP_IMAGE, DataUnit.POSTER_IMAGE, DataUnit.VOTE_AVERAGE,
                        DataUnit.CREATED_AT},
                DataUnit.ID + " =?",
                new String[]{String.valueOf(id)}, null);
    }

    @Override
    public void insertRecord(String targetTable, DataRecord record) {
        Uri resourceInFavorite = Repository.makeUri(targetTable);
        resourceInFavorite = ContentUris.withAppendedId(resourceInFavorite, record.getId());

        ContentValues values;
        if(targetTable.equals(DataProvider.FAVORITE_TABLE_NAME)){
            values = RepoUtils.getContentValuesOfFavoriteRecord(record);
        }else{
            values = RepoUtils.getContentValuesOfRecord(record);
        }
        final int INSERT_TOKEN_ID = 1;
        mQueryHandler.startInsert(INSERT_TOKEN_ID, null, resourceInFavorite, values);
    }

    @Override
    public synchronized void insertSetOfMovies(Uri resourceUri, List<Movie> setOfMovies) {
        for (Movie eachMovie : setOfMovies) {
            ContentValues values = RepoUtils.getContentValuesOfMovie(eachMovie);
            Uri uri = ContentUris.withAppendedId(resourceUri, eachMovie.getPrimaryFacts().getId());
            //mApp.getContentResolver().insert(uri, values);
            Log.d("INSERT", uri.toString());
            mQueryHandler.startInsert(0, null, uri, values);
        }
    }


    @Override
    public synchronized void updateRecord(final Movie infoToUpdate) {

        final long id = infoToUpdate.getPrimaryFacts().getId();
        ContentValues values = RepoUtils.getDynamicValuesOfMovie(mApp, infoToUpdate);

        final Uri uri = ContentUris.withAppendedId(makeUri(DataProvider.UPCOMING_TABLE_NAME), id);
        int updateToken = 0;
        mQueryHandler.startUpdate(updateToken, null, uri,
                values, DataUnit.ID + "=?", new String[]{String.valueOf(id)});

        final Uri uriPopular = ContentUris.withAppendedId(makeUri(DataProvider.POPULAR_TABLE_NAME), id);
        mQueryHandler.startUpdate(++updateToken, null, uriPopular,
                values, DataUnit.ID + "=?", new String[]{String.valueOf(id)});

        final Uri uriTheaters = ContentUris.withAppendedId(makeUri(DataProvider.THEATERS_TABLE_NAME), id);
        mQueryHandler.startUpdate(++updateToken, null, uriTheaters,
                values, DataUnit.ID + "=?", new String[]{String.valueOf(id)});

    }


    public synchronized void updateFavoriteStatus(Context app, String targetTable, DataRecord record){
        //values to update
        ContentValues values = new ContentValues();
        values.put(DataUnit.IS_FAVORITE, record.isFavorite());
        //the uri to reference the right element
        Uri resourceInUpcoming = Repository.makeUri(targetTable);
        resourceInUpcoming = ContentUris.withAppendedId(resourceInUpcoming, record.getId());
        //the token
        final int UPCOMING_UPDATE_TOKEN = 1;

        mQueryHandler.startUpdate(UPCOMING_UPDATE_TOKEN, null, resourceInUpcoming, values, DataUnit.ID + "=?",
                new String[]{String.valueOf(record.getId())});

        //Log.d("UpdateFavStatus", "Rows affected: " + rowsAffected);
    }

    @Override
    public synchronized void deleteRecord(Uri resourceUri, DataRecord record, Completion cb) {
        mQueryHandler.startDelete(0, cb, resourceUri, DataUnit.ID + "=?",
                new String[]{String.valueOf(record.getId())});
    }

    @Override
    public synchronized void clearData() {
        //To delete all the records from a table, we do not need to use WHERE clause with DELETE query
        Uri uri = makeUri(DataProvider.THEATERS_TABLE_NAME);
        mQueryHandler.startDelete(0, null, uri, null, null);
        uri = makeUri(DataProvider.UPCOMING_TABLE_NAME);
        mQueryHandler.startDelete(1, null, uri, null, null);
        uri = makeUri(DataProvider.POPULAR_TABLE_NAME);
        mQueryHandler.startDelete(2, null, uri, null, null);
    }


    /**
     * Schedule services to obtain data. The intents are saved in order to disarm the alarm manager
     * if needed later on.
     */
    public synchronized void scheduleServices(Application ctx, long interval) {
        if (pendingIntentList.isEmpty()) {
            pendingIntentList.add(ServicesUtils.prepareToRaiseNewService(
                    FetchUpcomingMoviesService.prepareIntentToSearch(ctx), ctx, interval));
            pendingIntentList.add(ServicesUtils.prepareToRaiseNewService(
                    FetchInTheatersMoviesService.prepareIntentToSearch(ctx), ctx, interval));
            pendingIntentList.add(ServicesUtils.prepareToRaiseNewService(
                    FetchPopularMoviesService.prepareIntentToSearch(ctx), ctx, interval));

            ServicesUtils.prepareToRaiseNewService(
                    NotificationReleaseService.prepareIntentToSearch(ctx), ctx, AlarmManager.INTERVAL_DAY);
        }
    }

    public void forceFetchOfNewData(Context ctx) {
        int code = 0;
        ServicesUtils.prepareToRaiseNewServiceAndForget(
                FetchUpcomingMoviesService.prepareIntentToSearch(ctx), ctx, code);
        ServicesUtils.prepareToRaiseNewServiceAndForget(
                FetchInTheatersMoviesService.prepareIntentToSearch(ctx), ctx, ++code);
        ServicesUtils.prepareToRaiseNewServiceAndForget(
                FetchPopularMoviesService.prepareIntentToSearch(ctx), ctx, ++code);
    }

    /**
     * Disarms and schedules the services with the new interval.
     */
    public synchronized void rescheduleServices(AlarmManager alarmManager, long interval) {
        disarmAlarm(alarmManager);
        setupAlarm(alarmManager, interval);
    }

    /**
     * Cancel all the services scheduled at the alarm manager.
     */
    private synchronized void disarmAlarm(AlarmManager alarmManager) {
        for (PendingIntent operation : pendingIntentList) {
            alarmManager.cancel(operation);
        }
    }

    /**
     * Schedule services at the alarm manager with the given time interval.
     */
    private synchronized void setupAlarm(AlarmManager alarmManager, long interval) {
        for (PendingIntent operation : pendingIntentList) {
            ServicesUtils.prepareToRaiseService(alarmManager, operation, interval, false);
        }
    }
}