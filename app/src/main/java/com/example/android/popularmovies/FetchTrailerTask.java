package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by abgeorge on 10/1/2016.
 * This class is an AsyncTask to fetch Movie trailer info.
 */
public class FetchTrailerTask extends AsyncTask<String, Void, Trailer[]> {

    private ArrayList<Trailer> trailers;
    private RecyclerView recyclerView;
    private TrailerAdapter trailerAdapter;
    private Context context;

    public FetchTrailerTask(Context context, RecyclerView recyclerView, TrailerAdapter trailerAdapter) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.trailerAdapter = trailerAdapter;
    }

    @Override
    protected Trailer[] doInBackground(String... params) {

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

            return MovieService.getTrailerDataFromJson(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Trailer[] trailers) {
        if (trailers != null) {
            this.trailers = new ArrayList<>(Arrays.asList(trailers));
            trailerAdapter = new TrailerAdapter(context, this.trailers);
            recyclerView.setAdapter(trailerAdapter);
        }
    }
}



