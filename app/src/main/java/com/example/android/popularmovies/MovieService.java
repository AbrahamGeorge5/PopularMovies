package com.example.android.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by abgeorge on 9/15/2016.
 * <p/>
 * Helper methods related to requesting and receiving movie data from themoviedb.
 */
public final class MovieService {
    public static final String LOG_TAG = MovieService.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link MovieService} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name MovieService (and an object instance of MovieService is not needed).
     */
    private MovieService() {
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static Movie[] getMovieDataFromJson(String jsonString) throws JSONException {

        String posterPath;
        String overview;
        String releaseDate;
        int movieId;
        String originalTitle;
        String originalLanguage;
        String title;
        String backdropPath;
        String popularity;
        int voteCount;
        String voteAverage;
        JSONObject result = new JSONObject(jsonString);
        JSONArray resultsArray = result.getJSONArray("results");
        Movie[] movies = new Movie[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movie = resultsArray.getJSONObject(i);
            posterPath = movie.getString("poster_path");
            overview = movie.getString("overview");
            releaseDate = movie.getString("release_date");
            movieId = movie.getInt("id");
            originalTitle = movie.getString("original_title");
            originalLanguage = movie.getString("original_language");
            title = movie.getString("title");
            backdropPath = movie.getString("backdrop_path");
            popularity = movie.getString("popularity");
            voteCount = movie.getInt("vote_count");
            voteAverage = movie.getString("vote_average");
            movies[i] = new Movie(posterPath, overview, releaseDate, movieId, originalTitle,
                    originalLanguage, title, backdropPath, popularity, voteCount, voteAverage);
        }
        return movies;
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static Trailer[] getTrailerDataFromJson(String jsonString) throws JSONException {

        String key;
        String name;
        String site;
        String type;
        JSONObject result = new JSONObject(jsonString);
        JSONArray resultsArray = result.getJSONArray("results");
        Trailer[] trailers = new Trailer[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject trailer = resultsArray.getJSONObject(i);
            key = trailer.getString("key");
            name = trailer.getString("name");
            site = trailer.getString("site");
            type = trailer.getString("type");
            trailers[i] = new Trailer(key, name, site, type);
        }
        Log.e(LOG_TAG, "Trailer object is " + trailers);
        return trailers;
    }

    public static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) {
        Log.v(LOG_TAG, "%%%%Inside makeHttpRequest()%%%%");
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException exception) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {

            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
