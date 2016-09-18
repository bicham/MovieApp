package com.example.ahmed.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.ahmed.movieapp.Models.Static;

public class FirstScrean extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_screan);



        final Animation animCycle = AnimationUtils.loadAnimation(this, R.anim.cycle);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.welcome_text);
        linearLayout.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout.startAnimation(animCycle);
            }
        }, 50);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (Static.checkInternet(getBaseContext()))
                {
                    Static.connectionStatus = true;
                    startActivity(new Intent(FirstScrean.this, Home.class));
                    finish();
                }else
                {
                    Static.favorite = true;
                    Static.connectionStatus = false;
                    startActivity(new Intent(FirstScrean.this, Home.class));
                    finish();
                }




            }
        }, 2000);



    }



}
