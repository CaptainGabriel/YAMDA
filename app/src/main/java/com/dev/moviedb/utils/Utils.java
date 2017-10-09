package com.dev.moviedb.utils;

/**
 * Utility class that holds general helper methods.
 *
 * @version 0.0.1
 */
public class Utils {

    /**
     * Use this method to create a string tag in an uniform way, every time.
     * @param cls the class interested in getting a tag
     * @return string tag
     */
    public static String makeLogTag(Class cls)
    {
        return cls.getSimpleName();
    }

}
