package com.example.ahmed.movieapp.Models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ahmed on 8/19/2016.
 */

public class Static {
    public static ArrayList<Data> results = new ArrayList<>();
    public static ArrayList<TrailersData> trailersDatas = new ArrayList<>();
    public static Data selectedMovie = new Data();
    public static Set<Integer> favoriteMovies = new HashSet<>();
    public static boolean favorite = false;
    public static View context;
    public static boolean mTwo;
    public static int list_position = 0;
    public static boolean check_if_first_time = true;
    public static boolean isFavoriteEmpty;
    public static boolean connectionStatus;

    public static SparseBooleanArray selectedItems = new SparseBooleanArray();


    public static boolean checkInternet(Context context)
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connect =  activeNetworkInfo != null && activeNetworkInfo.isConnected();


        return connect;
    }



}
