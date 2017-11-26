package com.dev.moviedb.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.service.UpdateItemDetailsService;

import java.util.Random;

/**
 * This class contains helper methods that perform some sort of action related
 * to the services this application has defined.
 *
 * @version 0.0.2
 */
public class ServicesUtils {

    private ServicesUtils() { /* No instances */}

    /**
     * This method helps raising a service. It can be any of the created services in this
     * application
     * since they only depend on the intent.
     *
     * @param customIntent Intent used to create the pending intent.
     * @param ctx          The application context.
     * @param intervalTime The amount of time between services
     */
    public static PendingIntent prepareToRaiseNewService(Intent customIntent, Context ctx, long intervalTime) {
        final AlarmManager alarmManager = ((YamdaApplication)ctx.getApplicationContext()).getAlarmManager();
        final PendingIntent pendingIntent = PendingIntent.getService(ctx, 0, customIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return prepareToRaiseService(alarmManager, pendingIntent, intervalTime, false);
    }


    /**
     * This method helps raising a service. It can be any of the created services in this
     * application since they only depend on the intent.
     *
     * @param pendingIntent PendingIntent used as the operation to schedule.
     * @param intervalTime  The amount of time between services
     */
    public static PendingIntent prepareToRaiseService(AlarmManager alarmManager,
                                                      PendingIntent pendingIntent,
                                                      long intervalTime,
                                                      boolean startNow) {
        intervalTime += getRandomDelay();
        long now = (startNow) ? 0 : intervalTime;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, intervalTime, pendingIntent);
        return pendingIntent;
    }


    /**
     * Utility method that helps starting a service that will run only once.
     *
     * @param customIntent The intent that specifies the service
     * @param ctx          The application's context
     * @param code         The code used to identify a particular execution inside the alarm
     *                     manager.
     * @return The pending intent representing this execution
     */
    public static PendingIntent prepareToRaiseNewServiceAndForget(Intent customIntent, Context ctx, long code) {
        int interval = getRandomDelay();
        final PendingIntent pendingIntent = PendingIntent.getService(ctx, (int) code, customIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mang = (AlarmManager) ctx.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mang.set(AlarmManager.RTC, System.currentTimeMillis() + interval, pendingIntent);
        return pendingIntent;
    }

    /**
     * Return a random number to work as time delay when requesting the web api.
     */
    private static int getRandomDelay() {
        Random r = new Random();
        return r.nextInt(5000) + 5000;
    }


    /**
     * Utility method that starts searching for details of a given movie.
     */
    public static void askForDetails(Context app, Movie movie) {
        Intent updateServiceIntent =
                UpdateItemDetailsService.prepareIntentToSearch(app.getApplicationContext(), movie);

        prepareToRaiseNewServiceAndForget(updateServiceIntent, app.getApplicationContext(),
                movie.getPrimaryFacts().getId());
    }

}
