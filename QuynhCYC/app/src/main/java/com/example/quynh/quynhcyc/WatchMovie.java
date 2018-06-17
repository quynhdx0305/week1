package com.example.quynh.quynhcyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.quynh.quynhcyc.data.trailerfilm.ItfaceTrailer;
import com.example.quynh.quynhcyc.data.trailerfilm.TrailerFilm;
import com.example.quynh.quynhcyc.data.trailerfilm.Youtube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchMovie extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView ytpv;
    private final String API_KEY_YT = "AIzaSyD0JMHIi4KjS8biazm4FgBWLu88uGhPeO8";
    Integer id_film;
    Integer position_film;
    String link_film = "jpLQYuh2U4U";
    boolean rtf_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);
        //youtubeplayerview
        ytpv = (YouTubePlayerView) findViewById(R.id.ytpv);

        id_film = getIntent().getIntExtra("id",0);
        position_film = getIntent().getIntExtra("position",0);
        //Toast.makeText(this, "id  "+  id_film , Toast.LENGTH_LONG).show();
        fun_retrofit(id_film);
    }

    public void run_video(){
        ytpv.initialize(API_KEY_YT, this);
    }

    public void fun_retrofit(Integer id_film){
        String BASE_URL = "https://api.themoviedb.org/3/movie/" + id_film + "/";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ItfaceTrailer itfaceTrailer = retrofit.create(ItfaceTrailer.class);
        Call<TrailerFilm> call = itfaceTrailer.getKey();
        call.enqueue(new Callback<TrailerFilm>() {
            @Override
            public void onResponse(Call<TrailerFilm> call, Response<TrailerFilm> response) {
                TrailerFilm trailerFilm = response.body();
                //Toast.makeText(getApplicationContext(),trailerFilm.toString(),Toast.LENGTH_LONG).show();
                //lay link youtube tu class Youtube
                Youtube youtube = trailerFilm.getYoutube().get(position_film);
                link_film = youtube.getSource();
                //Log.d("link film",link_film);
                run_video();
            }

            @Override
            public void onFailure(Call<TrailerFilm> call, Throwable t) {
            }
        });
    }


    //call = run video
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // https://www.youtube.com/watch?v=jpLQYuh2U4U
        if (!b) {
            //youTubePlayer.cueVideo(link_film);
            youTubePlayer.loadVideo(link_film);
            youTubePlayer.setFullscreen(true);
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = String.format("Error initializing YouTube player", youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}
