package com.example.ahmed.movieapp.Models.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.movieapp.Models.Data;
import com.example.ahmed.movieapp.Models.Holders.HolderGridView;
import com.example.ahmed.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/12/2016.
 */

public class CustomAdapterGridView extends RecyclerView.Adapter<HolderGridView>{

    Context context;
    private ArrayList<Data> results = new ArrayList<>();



    public CustomAdapterGridView(ArrayList<Data> results){
        this.results = results;

    }

    @Override
    public HolderGridView onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gridview_home_fragment, parent, false);

        context = parent.getContext();

        return new HolderGridView(view,results);
    }



    Data dataObject;
    int index;

    @Override
    public void onBindViewHolder(HolderGridView holderGridView, int position) {

        holderGridView.progressBar.setVisibility(View.VISIBLE);
        dataObject = results.get(position);
       Picasso.with(context).load("http://image.tmdb.org/t/p/w185//"+dataObject.poster_path)
////                .placeholder(R.drawable.progress)
              .into(holderGridView.imageView);

        holderGridView.progressBar.setVisibility(View.GONE);
//        index = position;
//        holderGridView.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dataObject = results.get(index);
//                Intent intent = new Intent(context,MovieDeatails.class);
//
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(dataObject);
//                intent.putExtra("movie_data",jsonString);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
