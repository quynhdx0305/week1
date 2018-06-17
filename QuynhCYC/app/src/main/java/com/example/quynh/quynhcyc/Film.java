package com.example.quynh.quynhcyc;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ViewTarget;

public class Film {
    private String HinhAnh;
    private String Ten;
    private Double VoteAverage;
    private Integer Id;
    private String Overview;
    private String Release_date;

    public Film(String hinhAnh, String ten, Double voteAverage, Integer id, String overview, String release_date) {
        HinhAnh = hinhAnh;
        Ten = ten;
        VoteAverage = voteAverage;
        Id = id;
        Overview = overview;
        Release_date = release_date;
    }

    public Double getVoteAverage() {
        return VoteAverage;
    }
    public void setVoteAverage(Double voteAverage) {
        VoteAverage = voteAverage;
    }

    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }

    public String getOverview() {
        return Overview;
    }
    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getRelease_date() {
        return Release_date;
    }
    public void setRelease_date(String release_date) {
        Release_date = release_date;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }
    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTen() {
        return Ten;
    }
    public void setTen(String ten) {
        Ten = ten;
    }
}
