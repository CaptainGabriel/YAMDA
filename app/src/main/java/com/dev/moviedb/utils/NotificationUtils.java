package com.dev.moviedb.utils;

import android.app.Application;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Provides easy creation of user notifications.
 *
 * @version 0.0.2
 */
public class NotificationUtils {

    private NotificationUtils(){}


    /**
     * Invoke to create a notification.
     *
     * @param appCtx
     *      The application's context
     * @param iconID
     *      The id of the icon to display in the notification
     * @param title
     *      The title of the notification
     * @param content
     *      The content to display inside the notification
     */
    public static void simpleNotification(Application appCtx, int iconID, String title, String content){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appCtx)
                        .setSmallIcon(iconID)
                        .setContentTitle(title)
                        .setContentText(content);

        NotificationManagerCompat.from(appCtx).notify(0, mBuilder.build());
    }

}

