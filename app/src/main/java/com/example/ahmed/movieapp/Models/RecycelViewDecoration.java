package com.example.ahmed.movieapp.Models;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ahmed on 8/19/2016.
 */

public class RecycelViewDecoration extends RecyclerView.ItemDecoration {

    private int decoration = 0;
    public RecycelViewDecoration(int decoration)
    {
        this.decoration = decoration;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = decoration;
        outRect.left = decoration;
        outRect.right = decoration;

        if(parent.getChildLayoutPosition(view) == 0)
        {
            outRect.top = decoration;
        }
        else
        {
            outRect.top = 0;
        }
    }
}
