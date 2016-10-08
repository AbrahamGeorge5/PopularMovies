package com.example.android.popularmovies;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private static final String TMDB_TRAILER_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "";
    private static final String REQUEST_VIDEO = "videos";
    public LinearLayoutManager linearLayoutManager;
    public ArrayList<Trailer> trailers = new ArrayList<>();

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Movie movie = getActivity().getIntent().getParcelableExtra("movie");
        Uri baseUri = Uri.parse(TMDB_TRAILER_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(String.valueOf(movie.getMovieId()));
        uriBuilder.appendPath(REQUEST_VIDEO);
        uriBuilder.appendQueryParameter("api_key", API_KEY);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.TransparentText);

        ImageView movieBackdrop = (ImageView) rootView.findViewById(R.id.movieBackdrop);
        Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getBackdropPath()).fit().centerCrop().into(movieBackdrop);

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.moviePoster);
        Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).fit().centerCrop().into(moviePoster);


        TextView movieTitle = (TextView) rootView.findViewById(R.id.movieTitle);
        movieTitle.setText(movie.getTitle());

        TextView movieReleaseYear = (TextView) rootView.findViewById(R.id.movieYear);
        String month = getMonth(movie.getReleaseDate());
        movieReleaseYear.setText(month + " " + movie.getReleaseDate().substring(0, 4));

        TextView movieOverview = (TextView) rootView.findViewById(R.id.movieOverview);
        movieOverview.setText(movie.getOverview());


        RecyclerView trailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.horizontal_recycler_view);
        linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);

        TrailerAdapter trailerAdapter = new TrailerAdapter(getActivity().getApplicationContext(), trailers);
        trailerRecyclerView.setAdapter(trailerAdapter);
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        trailerRecyclerView.setNestedScrollingEnabled(true);
//        trailerRecyclerView.setHasFixedSize(true);


        new FetchTrailerTask(getContext(), trailerRecyclerView, trailerAdapter).execute(uriBuilder.toString());

        String title = movie.getTitle();
        collapsingToolbar.setTitle(title);

        return rootView;
    }

    private String getMonth(String date) {
        String value = "";
        String monthExtract = date.substring(5, 7);
        int month = Integer.parseInt(monthExtract);
        switch (month) {
            case 1:
                value = "January";
                break;
            case 2:
                value = "February";
                break;
            case 3:
                value = "March";
                break;
            case 4:
                value = "April";
                break;
            case 5:
                value = "May";
                break;
            case 6:
                value = "June";
                break;
            case 7:
                value = "July";
                break;
            case 8:
                value = "August";
                break;
            case 9:
                value = "September";
                break;
            case 10:
                value = "Ocotber";
                break;
            case 11:
                value = "November";
                break;
            case 12:
                value = "December";
                break;

        }
        return value;
    }
}
