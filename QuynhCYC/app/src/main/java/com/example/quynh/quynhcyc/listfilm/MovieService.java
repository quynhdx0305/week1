package com.example.quynh.quynhcyc.listfilm;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("now_playing?api_key=112d715d8451638b8a32f972ba78c021")
    Call<PlayNow> getService();
}
