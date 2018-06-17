package com.example.quynh.quynhcyc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    ArrayList<Film> film;
    Context context;
    String imgUrlbase = "http://image.tmdb.org/t/p/w200";

    // contactor cua 2 bien dataShop va context
    public FilmAdapter(ArrayList<Film> film, Context context) {
        this.film = film;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.film,parent,false);
        return new ViewHolder(itemView);// goi class ViewHolder ben duoi
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // load hinh
        Glide.with(context).load(imgUrlbase + film.get(position).getHinhAnh())
                .into(holder.imgHinh);
        // load chu
        holder.txtName.setText(film.get(position).getTen());
        holder.txtVoteAverage.setText(film.get(position).getVoteAverage().toString());
        holder.txtOverview.setText(film.get(position).getOverview());
        holder.txtRelease_date.setText(film.get(position).getRelease_date());

    }

    @Override
    public int getItemCount() {
        return film.size();
    }

    // tao class ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgHinh;
        TextView txtName;
        TextView txtVoteAverage;
        TextView txtId;
        TextView txtOverview;
        TextView txtRelease_date;

        public ViewHolder(View itemView) {
            super(itemView);
            imgHinh = (ImageView)itemView.findViewById(R.id.imgHinh);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtVoteAverage = (TextView)itemView.findViewById(R.id.txtVoteAverage);
            //txtId = (TextView)itemView.findViewById(R.id.txtId);
            txtOverview = (TextView)itemView.findViewById(R.id.txtOverview);
            txtRelease_date = (TextView)itemView.findViewById(R.id.txtRelease_date);

            itemView.setOnClickListener(this);
        }

        // fun click
        @Override
        public void onClick(View v) {
            //bo phim nao
            int position = getLayoutPosition();
            Log.d("position", String.valueOf((position)));
            Toast.makeText(context, "Click :" + String.valueOf((position)), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context,WatchMovie.class);
            intent.putExtra("id",film.get(position).getId());
            intent.putExtra("position", (position));
            context.startActivity(intent);
        }
    }
}
