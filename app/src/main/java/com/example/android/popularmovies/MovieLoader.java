package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.util.List;

/**
 * Created by abgeorge on 9/15/2016.
 */
public class MovieLoader extends AsyncTaskLoader<List<Movie>>{

    public static final String LOG_TAG = MovieLoader.class.getName();
    private String url;

    public MovieLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    @Override
    public List<Movie> loadInBackground() {
        Log.v(LOG_TAG, "Entered loadInBackground method.");
        URL url = MovieService.createURL(this.url);
        List<Movie> movies = null;
        String jsonResponse = "";
        try{
            jsonResponse = MovieService.makeHttpRequest(url);
            //movies = MovieService.extractMovies(jsonResponse);
        } catch (Exception e) {

        }
        Log.v(LOG_TAG, "List of movies are" + movies);
        return movies;

    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "%%%%Inside onStartLoading()%%%%");
        forceLoad();
    }
}
