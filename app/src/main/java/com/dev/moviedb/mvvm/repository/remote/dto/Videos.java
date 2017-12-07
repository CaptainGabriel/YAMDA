package com.dev.moviedb.mvvm.repository.remote.dto;

/**
 * Yamda 1.0.0.
 */
public class Videos
{
    public Results[] results;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+"]";
    }
}