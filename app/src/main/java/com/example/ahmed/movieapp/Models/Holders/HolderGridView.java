package com.example.ahmed.movieapp.Models.Holders;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ahmed.movieapp.Models.Data;
import com.example.ahmed.movieapp.Models.Static;
import com.example.ahmed.movieapp.MovieDeatails;
import com.example.ahmed.movieapp.MovieDeatailsFragment;
import com.example.ahmed.movieapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Created by Ahmed on 8/19/2016.
 */

public class HolderGridView extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public ImageView imageView;
    public ProgressBar progressBar;

    public ArrayList<Data> results = new ArrayList<>();


    public HolderGridView(View view, ArrayList<Data> results)
    {
        super(view);
        view.setOnClickListener(this);

        this.results=results;
        imageView = (ImageView) view.findViewById(R.id.row_gridView_Image);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        Data dataObject = results.get(getPosition());

//        if (Static.selectedItems.get(getAdapterPosition(),false))
//        {
//            Static.selectedItems.delete(getAdapterPosition());
//            v.setSelected(false);
//        }
//        else
//        {
//            Static.selectedItems.put(getAdapterPosition(),true);
//            v.setSelected(true);
//        }


        Static.check_if_first_time = false;
        Static.list_position = getPosition();

        Static.selectedMovie = dataObject;
        Static.context = v;







        if (Static.mTwo == false)
        {
            Intent intent = new Intent(v.getContext(),MovieDeatails.class);
            Gson gson = new Gson();
            String jsonString = gson.toJson(dataObject);
            intent.putExtra("movie_data",jsonString);

            v.getContext().startActivity(intent);

        }
        else
        {
            ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment,new MovieDeatailsFragment(),"DFTAG")
                    .commit();


//            v.getContext().startActivity(new Intent(v.getContext(), Home.class));
//            ((Activity)v.getContext()).finish();

        }

    }
}
