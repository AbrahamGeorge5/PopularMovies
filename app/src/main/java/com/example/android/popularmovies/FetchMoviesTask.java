package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by abgeorge on 9/15/2016.
 * This class is an AsyncTask to fetch the Movie info.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private Context context;

    public FetchMoviesTask(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected Movie[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String jsonString = null;

        try {
            String uri = params[0];
            URL url = MovieService.createURL(uri);
            jsonString = MovieService.makeHttpRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            return MovieService.getMovieDataFromJson(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] popularMovies) {
        if (popularMovies != null) {
            movies = new ArrayList<>(Arrays.asList(popularMovies));
            movieAdapter = new MovieAdapter(context, movies, recyclerView);
            recyclerView.setAdapter(movieAdapter);
        }
    }
}


