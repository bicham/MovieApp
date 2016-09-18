package com.example.ahmed.movieapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ahmed.movieapp.Models.Connection;
import com.example.ahmed.movieapp.Models.CustomAdapters.CustomAdapterTrailer;
import com.example.ahmed.movieapp.Models.Data;
import com.example.ahmed.movieapp.Models.DataBase.DataBase;
import com.example.ahmed.movieapp.Models.ReviewDataList;
import com.example.ahmed.movieapp.Models.Static;
import com.example.ahmed.movieapp.Models.TrailersDataList;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.ahmed.movieapp.Models.Static.selectedMovie;

/**
 * A placeholder fragment containing a simple view.
 */


public class MovieDeatailsFragment extends Fragment {

    public RecyclerView myRecyclerView;
    public RecyclerView.Adapter myAdapter;
    public LinearLayoutManager myLayout;


    public DataBase myDataBase;
    public ReviewDataList reviewObject;
    public TrailersDataList trailersDataList;
    boolean insertion;
    boolean lost_connection = false;


    public MovieDeatailsFragment() {
    }


    private Target target;
    private Target target_1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_movie_deatails, container, false);
        final LinearLayout linearContainer_title = (LinearLayout) view.findViewById(R.id.linearContainer_title);



        myDataBase = new DataBase(getContext(),null,null,1);
        reviewObject = new ReviewDataList();
        trailersDataList = new TrailersDataList();

        // create this target for put image from picasso to linearLayout background

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                linearContainer_title.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmap));

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

                linearContainer_title.setBackground(view.getResources().getDrawable(R.drawable.home));

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }


        };
        Intent intent = getActivity().getIntent();
        String movie_data = "";

        TextView title = (TextView) view.findViewById(R.id.details_title_text);
        TextView date = (TextView) view.findViewById(R.id.details_date_text);
        TextView overview = (TextView) view.findViewById(R.id.details_overview_text);


        RatingBar rate = (RatingBar) view.findViewById(R.id.details_rating);

        ImageView image = (ImageView) view.findViewById(R.id.details_Image);


        Button addToFavoritButton = (Button) view.findViewById(R.id.details_favorite_button);
        Button reviewButton = (Button) view.findViewById(R.id.details_review_button);

        addToFavoritButton.setContentDescription("add t o favorite");

        final LinearLayout linearContainer = (LinearLayout) view.findViewById(R.id.linearContainer);


        if(Static.isFavoriteEmpty == true && Static.favorite == true)
        {
            title.setVisibility(View.GONE);
            date.setVisibility(View.GONE);
            overview.setVisibility(View.GONE);
            rate.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            addToFavoritButton.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);
            linearContainer.setVisibility(View.GONE);
            linearContainer_title.setVisibility(View.GONE);

        }
        else
        {
            Data dataObject = Static.selectedMovie;
//        if(intent != null && intent.hasExtra("movie_data"))
//        {
//            movie_data = intent.getStringExtra("movie_data");
//            Gson gson = new Gson();
            //final Data dataObject = gson.fromJson(movie_data,Data.class);








            ///getting review data

            try {


                if(Static.favorite == false)
                {
                    if(!Static.checkInternet(getContext()))
                    {
                        lost_connection = true;
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(),android.R.style.Theme_Holo_Dialog)
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        alert.setTitle("Alert");
                        // alert.setIcon(android.R.drawable.ic_dialog_alert);

                        alert.setMessage("Sorry! You lost the internet connection if you want to go to the favorite list " +
                                "tap ok or you can tap close to continue without reviews or trailers");




                        alert.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                Static.connectionStatus = false;
//                                Static.favorite = true;
//                                startActivity(new Intent(getContext(),Home.class));
//                                getActivity().finish();
//                                ((Activity) Static.context.getContext()).finish();

                            }
                        });

                        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                                ((Activity) Static.context.getContext()).finish();
                            }
                        });



                        alert.show();
                    }
                    else
                    {
                        //get review data if user come from home
                        String uriReview = "https://api.themoviedb.org/3/movie/"+Static.selectedMovie.id+"/reviews?api_key="+BuildConfig.MOVIE_API_KEY;
                        String resultReview = null;

                        resultReview = new Connection().execute(uriReview).get();
                        final Gson gsonReview = new Gson() ;
                        reviewObject = gsonReview.fromJson(resultReview,ReviewDataList.class);
                    }


                }
                else
                {
                    // get review data if user come from favorite

                    reviewObject = myDataBase.getReviewsData(dataObject.id);


                }

                reviewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (reviewObject.total_results <= 0)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext(),android.R.style.Theme_Holo_Light_Dialog)
                                    .setIcon(android.R.drawable.ic_dialog_alert);
                            alert.setTitle("Alert");
                            // alert.setIcon(android.R.drawable.ic_dialog_alert);
                            alert.setMessage("Sorry! There is no review for this movie yet.");


                            alert.setNegativeButton("OK!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });



                            alert.show();





                        }
                        else
                        {
                            ArrayList<String> spinner = new ArrayList<String>();
                            for (int j = 0 ; j < reviewObject.results.size();j++)
                            {
                                int x = j+1;
                                String s = x + "- " + reviewObject.results.get(j).author;
                                spinner.add(s);
                            }

                            final View view1 = v;
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,spinner);
                            new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Light_Dialog).setIcon(android.R.drawable.btn_star_big_on).setTitle("Select author")
                                    .setAdapter(spinnerAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewObject.results.get(which).url));
                                            view1.getContext().startActivity(intent);

                                            dialog.dismiss();

                                        }
                                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();


                        }

                    }
                });





            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }







            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500//"+dataObject.backdrop_path).error(R.drawable.home).into(target);
//                    .networkPolicy(NetworkPolicy.OFFLINE).into(new Target(){
//
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    linearContainer_title.setBackground(new BitmapDrawable(getContext().getResources(), bitmap));
//                }
//
//                @Override
//                public void onBitmapFailed(final Drawable errorDrawable) {
//                    Log.d("TAG", "FAILED");
//                }
//
//                @Override
//                public void onPrepareLoad(final Drawable placeHolderDrawable) {
//
//                    Log.d("TAG", "Prepare Load");
//                }
//            });



            target_1 = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    linearContainer.setBackground(new BitmapDrawable(view.getContext().getResources(), bitmap));

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                    linearContainer.setBackground(view.getResources().getDrawable(R.drawable.home));
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }


            };

            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500//"+dataObject.poster_path).error(R.drawable.home)
                    .into(target_1);


//            if(dataObject.title.length() > 15 && dataObject.title.length() < 22) {
//
//                title.setTextScaleX(0.5f);
//            }
//            else if(dataObject.title.length() > 21 && dataObject.title.length() < 40)
//            {
//                title.setTextScaleX(0.5f);
//            }
            title.setText(dataObject.title);

            date.setText(dataObject.release_date.substring(0,4));
            overview.setText(dataObject.overview);
            overview.setEnabled(false);


            int x = (int) dataObject.vote_average;
            rate.setRating(x);
            Drawable progress = rate.getProgressDrawable();
            int s = getResources().getColor(R.color.title_text_color);
            DrawableCompat.setTint(progress,s);


            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//"+dataObject.poster_path)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {


                        }

                        @Override
                        public void onError() {


                        }
                    });


            ///////// trailer recycler view



            try{


                if(Static.favorite == false)
                {

                    if(!lost_connection)
                    {
                        String KEY = BuildConfig.MOVIE_API_KEY;
                        String uri = "https://api.themoviedb.org/3/movie/"+dataObject.id+"/videos?api_key="+KEY;
                        String result =  new Connection().execute(uri).get();
                        final Gson trailerGson = new Gson() ;
                        trailersDataList = trailerGson.fromJson(result,TrailersDataList.class);
                        Static.trailersDatas = trailersDataList.results;
                    }

                }
                else
                {
                    trailersDataList = myDataBase.getMovieTrailers(dataObject.id);
                    Static.trailersDatas = trailersDataList.results;
                }




                 myAdapter = new CustomAdapterTrailer(trailersDataList.results);
                myRecyclerView = (RecyclerView) view.findViewById(R.id.trailer_recyclerView) ;
                myRecyclerView.setHasFixedSize(true);
                myLayout = new LinearLayoutManager(getContext());
                myLayout.setOrientation(LinearLayoutManager.VERTICAL);
                myRecyclerView.setLayoutManager(myLayout);
                myRecyclerView.setAdapter(myAdapter);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }





            //}



            if(Static.favoriteMovies.contains(selectedMovie.id))
            {
                addToFavoritButton.setVisibility(View.GONE);
            }
            else
            {
                addToFavoritButton.setVisibility(View.VISIBLE);
            }




            addToFavoritButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(!Static.checkInternet(v.getContext()))
                    {




                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext(),android.R.style.Theme_Holo_Dialog)
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        alert.setTitle("Alert");
                         alert.setIcon(android.R.drawable.ic_dialog_alert);

                            alert.setMessage("Sorry! there is no internet connection you can't add any movies to the favorite list right now " +
                                    " please check you connection and try again");




                        alert.setNegativeButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });



                        alert.show();



                    }
                    else
                    {
                        insertion = myDataBase.Insert(selectedMovie,trailersDataList,reviewObject);


                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext(),android.R.style.Theme_Holo_Dialog)
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        alert.setTitle("Adding");
                        // alert.setIcon(android.R.drawable.ic_dialog_alert);
                        if(insertion == false)
                        {
                            alert.setMessage("Sorry! some thing went wrong try again later.");
                        }
                        else
                        {
                            alert.setMessage("Congrats! Movie added successfully");
                        }



                        alert.setNegativeButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Static.favorite = true;
                                startActivity(new Intent(getContext(),Home.class));
                                getActivity().finish();
                                ((Activity) Static.context.getContext()).finish();
                            }
                        });



                        alert.show();



                    }







                }
            });


        }




        return view;
    }


}
