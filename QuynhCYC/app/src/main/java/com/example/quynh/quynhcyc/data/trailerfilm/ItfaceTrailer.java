package com.example.quynh.quynhcyc.data.trailerfilm;

import com.example.quynh.quynhcyc.listfilm.PlayNow;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItfaceTrailer {
    @GET("trailers?api_key=112d715d8451638b8a32f972ba78c021")
    Call<TrailerFilm> getKey();
}
