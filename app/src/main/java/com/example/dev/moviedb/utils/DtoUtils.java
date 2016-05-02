package com.example.dev.moviedb.utils;


/**
 * Helper methods related to DTO objects.
 *
 * @version 0.0.1
 */
public class DtoUtils {

    private DtoUtils(){
        //no instance
    }



    public static String transformRuntime(int rawRuntime){
        return (rawRuntime > 0) ? String.format("%dh%d", rawRuntime / 60,rawRuntime % 60) : "0h0";
    }



}
