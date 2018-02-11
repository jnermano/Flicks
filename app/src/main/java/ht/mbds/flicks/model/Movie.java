package ht.mbds.flicks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ermano
 * on 2/11/2018.
 */

public class Movie {

    private long id;
    private long vote_count;
    private boolean video;
    private double vote_average;
    private double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;

    public static Movie fromJson(JSONObject jsonObject) {
        Movie movie = new Movie();

        try {

            movie.id = jsonObject.getLong("id");
            movie.vote_count = jsonObject.getLong("vote_count");
            movie.video = jsonObject.getBoolean("video");
            movie.vote_average = jsonObject.getDouble("vote_average");
            movie.popularity = jsonObject.getDouble("popularity");
            movie.poster_path = jsonObject.getString("poster_path");
            movie.original_language = jsonObject.getString("original_language");
            movie.original_title = jsonObject.getString("original_title");
            movie.backdrop_path = jsonObject.getString("backdrop_path");
            movie.adult = jsonObject.getBoolean("adult");
            movie.overview = jsonObject.getString("overview");
            movie.release_date = jsonObject.getString("release_date");

        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movie;
    }

    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject movieJson;

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                movieJson = jsonArray.getJSONObject(i);
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }

            Movie movie = Movie.fromJson(movieJson);
            if (movie != null) {
                movies.add(movie);
            }
        }

        return movies;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
