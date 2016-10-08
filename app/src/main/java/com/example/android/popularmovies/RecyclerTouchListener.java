package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by abgeorge on 9/17/2016.
 */
public class RecyclerTouchListener implements View.OnClickListener {
    private Context context;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;

    public RecyclerTouchListener(Context context, ArrayList<Movie> movies, RecyclerView recyclerView) {
        this.context = context;
        this.movies = movies;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View view) {
//        int itemPosition = recyclerView.getChildLayoutPosition(view);
//        Movie movie = movies.get(itemPosition);
//        Toast toast = Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT);
//        toast.show();


    }
}
