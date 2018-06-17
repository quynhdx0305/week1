package com.example.quynh.quynhcyc;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.quynh.quynhcyc.listfilm.MovieService;
import com.example.quynh.quynhcyc.listfilm.PlayNow;
import com.example.quynh.quynhcyc.listfilm.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Result> resultList;// list movie
    ArrayList<Film> filmList = new ArrayList<>();
    SwipeRefreshLayout swipeLayout;
    //boolean refesh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout = findViewById(R.id.activity_swipe_fresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fun_retrofit();
                Toast.makeText(MainActivity.this,"Refesh",Toast.LENGTH_LONG).show();

                // To keep animation for 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeLayout.setRefreshing(false);
                    }
                }, 1500); // Delay in millis
            }
        });

        fun_retrofit();
    }

    //xoay man hinh ko thay doi giu lieu
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void fun_initView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);// the nay toi uu hoa du lieu
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //duong ngan cach
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        // chay film list
        FilmAdapter filmAdapter = new FilmAdapter(filmList,getApplicationContext());
        recyclerView.setAdapter(filmAdapter);

        Log.d("fun_initView","OK");
    }

    public void fun_retrofit() {
        String BASE_URL = "https://api.themoviedb.org/3/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<PlayNow> call = movieService.getService();
        call.enqueue(new Callback<PlayNow>() {
            @Override
            public void onResponse(Call<PlayNow> call, Response<PlayNow> response) {
                PlayNow playNow = response.body();//Json data
                resultList = playNow.getResults();//Json of result
                for(Result result:resultList)// ket qua tung phan tu trong list
                {
                    //Toast.makeText(MainActivity.this,result.getId().toString(),Toast.LENGTH_LONG).show();
                    //hinh anh,ten,voteAverage,id,overview,release_date add to filmList
                    filmList.add(new Film(result.getPosterPath() ,result.getTitle(),
                                result.getVoteAverage(),
                            result.getId(),result.getOverview(),result.getReleaseDate()));
                }
                    fun_initView();
                //Toast.makeText(MainActivity.this,resultList.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PlayNow> call, Throwable t) {
                Log.d("fun_retrofit","fail");
            }
        });
    }
}
