package com.example.ahmed.movieapp.Models.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.movieapp.Models.Holders.HolderTrailer;
import com.example.ahmed.movieapp.Models.Static;
import com.example.ahmed.movieapp.Models.TrailersData;
import com.example.ahmed.movieapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/25/2016.
 */

public class CustomAdapterTrailer extends RecyclerView.Adapter<HolderTrailer> {

    Context context;
    private ArrayList<TrailersData> results = new ArrayList<>();


    public CustomAdapterTrailer(ArrayList<TrailersData> results){
        this.results = results;

    }

    @Override
    public HolderTrailer onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_trailer_custom, parent, false);
        context = parent.getContext();

        return new HolderTrailer(view,results);
    }

    private Target target;

    @Override
    public void onBindViewHolder(final HolderTrailer holder, int position) {

        TrailersData trailersData = results.get(position);

        holder.number_text.setText(position+1+"");

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.layout_container_image.setBackground(new BitmapDrawable(context.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        };

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500//"+ Static.selectedMovie.backdrop_path).into(target);

        holder.label_text_1.setText(trailersData.name);

        holder.label_text_2.setText(trailersData.site);


    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
