package com.example.ahmed.movieapp.Models.Holders;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ahmed.movieapp.Models.Static;
import com.example.ahmed.movieapp.Models.TrailersData;
import com.example.ahmed.movieapp.R;

import java.util.ArrayList;

/**
 * Created by Ahmed on 8/25/2016.
 */

public class HolderTrailer extends RecyclerView.ViewHolder implements View.OnClickListener {

    public LinearLayout layout_container;
    public TextView number_text;
    public RelativeLayout layout_container_image;
    public ImageView play_image;
    public TextView label_text_1;
    public TextView label_text_2;


    public ArrayList<TrailersData> results = new ArrayList<>();

    public HolderTrailer(View itemView,ArrayList<TrailersData> results) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.results = results;

        layout_container = (LinearLayout) itemView.findViewById(R.id.Layout_container);
        number_text = (TextView) itemView.findViewById(R.id.number_text);
        layout_container_image = (RelativeLayout) itemView.findViewById(R.id.layout_container_image);
        play_image = (ImageView) itemView.findViewById(R.id.play_image);
        label_text_1 = (TextView) itemView.findViewById(R.id.label_text_1);
        label_text_2 = (TextView) itemView.findViewById(R.id.label_text_2);

    }

    @Override
    public void onClick(View v) {

        String key = Static.trailersDatas.get(getPosition()).key;
        String uri = "https://www.youtube.com/watch?v="+key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        intent.setDataAndType(Uri.parse(uri), "video/mp4");
        v.getContext().startActivity(intent);

    }
}
