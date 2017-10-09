package com.dev.moviedb.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class created to remove boilerplate code related to the creation of
 * simple toasts. It also removes the need to remember to call the "show" method.
 *
 * @version 0.0.2
 */
public class ToastUtils {

    private ToastUtils() {
        //no instances
    }

    /**
     * Displays a Toast message with a big length.
     *
     * @param message
     *  The message to be displayed
     * @param context
     *      The application's context
     */
    public static void showError(final String message, final Context context) {
        getToast(message, context).show();
    }

    /**
     * Displays a Toast message with a small length.
     *
     * @param message
     *  The message to be displayed
     * @param context
     *      The application's context
     */
    public static void showShortMessage(String message, Context context) {
        getToast(message, context, Toast.LENGTH_SHORT).show();
    }

    private static Toast getToast(String message, Context context) {
        return getToast(message, context, Toast.LENGTH_LONG);
    }

    private static Toast getToast(String message, Context context, int length) {
        return Toast.makeText(context, message, length);
    }
}
