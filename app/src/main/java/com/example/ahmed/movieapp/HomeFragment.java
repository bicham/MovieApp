package com.example.ahmed.movieapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmed.movieapp.Models.Connection;
import com.example.ahmed.movieapp.Models.CustomAdapters.CustomAdapterGridView;
import com.example.ahmed.movieapp.Models.DataBase.DataBase;
import com.example.ahmed.movieapp.Models.DataList;
import com.example.ahmed.movieapp.Models.Static;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    public RecyclerView myRecyclerView;
    public RecyclerView.Adapter myAdapter;
    public RecyclerView.LayoutManager myLayout;

    public int list_position;

    public HomeFragment() {
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        if( Static.connectionStatus == false)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext(),android.R.style.Theme_Holo_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert);
            alert.setTitle("No Internet");
            // alert.setIcon(android.R.drawable.ic_dialog_alert);

                alert.setMessage("Sorry! there is no internet connection please check it.If you want to continue to the favorite list click" +
                        " ok if not click cancel");




            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });



            alert.show();

        }

        final Animation animCycle = AnimationUtils.loadAnimation(getContext(), R.anim.anothercycle);


        LinearLayout linear_no_favorites = (LinearLayout) view.findViewById(R.id.linear_no_favorites);
        linear_no_favorites.startAnimation(animCycle);

        TextView clickhere_text = (TextView) view.findViewById(R.id.clickhere_text);

//        DataBase d = new DataBase(getContext(),null,null,1);
//        d.deleteall();

        DataBase myDataBase = new DataBase(getContext(),null,null,1);

        DataList alldata = myDataBase.getAllMovies();

        for (int i = 0 ; i < alldata.results.size() ; i++)
        {
            Static.favoriteMovies.add(alldata.results.get(i).id);
        }


        if(alldata.results.size() == 0)
        {
            Static.isFavoriteEmpty = true;
        }
        else
        {
            Static.isFavoriteEmpty = false;
        }


        if(Static.favorite == false)
        {
            linear_no_favorites.setVisibility(View.GONE);
        }





        myRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view) ;
        myRecyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            myLayout = new GridLayoutManager(getContext(),3);
        }
        else
        {
            myLayout = new GridLayoutManager(getContext(),2);
        }

//            int decoration = getResources().getDimensionPixelOffset(R.dimen.decoration);
//            myRecyclerView.addItemDecoration(new RecycelViewDecoration(decoration));
        myRecyclerView.setLayoutManager(myLayout);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setMoveDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        itemAnimator.setChangeDuration(1000);
        itemAnimator.setAddDuration(1000);



        myRecyclerView.setItemAnimator(itemAnimator);



        try {


            if(Static.favorite == false)
            {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sort = preferences.getString(getString(R.string.settings_sort_key),getString(R.string.settings_sort_default_popular));
                String KEY = BuildConfig.MOVIE_API_KEY;
                String uri = "https://api.themoviedb.org/3/movie/"+sort+"?api_key="+KEY;

                String result =  new Connection().execute(uri).get();
                final Gson gson = new Gson() ;
                DataList ob = gson.fromJson(result,DataList.class);
                Static.results = ob.results;

                if (Static.check_if_first_time == true)
                {
                    Static.selectedMovie = Static.results.get(0);
                }

                myAdapter = new CustomAdapterGridView(ob.results);

            }
            else
            {

                if(alldata.results.size() != 0)
                {
                    linear_no_favorites.setVisibility(View.GONE);
                }

                 if(Static.check_if_first_time == true && alldata.results.size() != 0)
                {
                    Static.selectedMovie = alldata.results.get(0);
                }
                myAdapter = new CustomAdapterGridView(alldata.results);

            }


//            GridView gridView = (GridView) view.findViewById(R.id.mainactivity_gridView);



            myRecyclerView.setAdapter(myAdapter);

//            gridView.setAdapter(myAdapter);

//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Data dataObject = (Data) myAdapter.getItem(position);
//                    Intent intent = new Intent(getContext(),MovieDeatails.class);
//
//                    Gson gson1 = new Gson();
//                    String jsonString = gson1.toJson(dataObject);
//                    intent.putExtra("movie_data",jsonString);
//                    startActivity(intent);
//                }
//            });

            //Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        clickhere_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Static.checkInternet(v.getContext()))
                {
                    Static.favorite = true;
                    Static.connectionStatus = false;

                }
                else
                {
                    Static.favorite = false;
                    Static.connectionStatus = true;
                }

                startActivity(new Intent(getActivity(),Home.class));
                getActivity().finish();
            }
        });


        if (savedInstanceState != null && savedInstanceState.containsKey("selected_position"))
        {
            list_position = savedInstanceState.getInt("selected_position");
            myRecyclerView.getLayoutManager().scrollToPosition(list_position);

        }


        myRecyclerView.smoothScrollToPosition(Static.list_position);




        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("selected_position",Static.list_position);
            super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_home,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(),SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
}
