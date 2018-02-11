package ht.mbds.flicks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ht.mbds.flicks.model.Movie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {

    YouTubePlayerSupportFragment youTubePlayerView;

    @BindView(R.id.details_tv_title)
    TextView tv_title;

    @BindView(R.id.details_tv_desc)
    TextView tv_desc;

    @BindView(R.id.details_tv_release_date)
    TextView tv_release_date;

    @BindView(R.id.details_rating)
    RatingBar ratingBar;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bind views
        ButterKnife.bind(this);

        //get Intent
        Intent intent = getIntent();

        try {

            // get data from caller activity
            movie = Movie.fromJson(new JSONObject(intent.getStringExtra("movie")));

            if (movie.getVote_average() > 5) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            youTubePlayerView = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.details_video);

            setTitle(movie.getOriginal_title());
            tv_title.setText(movie.getOriginal_title());
            tv_desc.setText(movie.getOverview());
            tv_release_date.setText(String.format(Locale.US, "Release date : %s", movie.getRelease_date()));
            ratingBar.setRating((float) (movie.getVote_average() / 2));

            // Loag video list form the current movie
            loadVideo(movie.getId());


        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadVideo(long id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    final JSONArray jsonArray = jsonObject.getJSONArray("results");

                    final String videoKey = jsonArray.getJSONObject(0).getString("key");

                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            youTubePlayerView.initialize("AIzaSyAxCgaNNdZBXM8xoOHwVrWyXr5_aSV9J3M",
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {

                                            // do any work here to cue video, play video, etc.


                                            if(movie.getVote_average() > 5) {
                                                youTubePlayer.loadVideo(videoKey);
                                            } else {
                                                youTubePlayer.cueVideo(videoKey);
                                            }

                                        }

                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult youTubeInitializationResult) {

                                        }
                                    });

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
