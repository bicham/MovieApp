package com.example.ahmed.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ahmed.movieapp.Models.Static;

public class Home extends AppCompatActivity {


    public static final String details_fragment_tag = "DFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);





        if (findViewById(R.id.details_fragment) != null)
        {
            Static.mTwo = true;
            if (savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.details_fragment, new MovieDeatailsFragment(),details_fragment_tag)
                        .commit();
            }
        }
        else
        {
            Static.mTwo = false;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem favorit_Item = menu.findItem(R.id.action_favorite);
        MenuItem home_Item = menu.findItem(R.id.action_home);

        if(Static.favorite == false)
        {
            favorit_Item.setVisible(true);
            home_Item.setVisible(false);

            setTitle("Home");
        }
        else
        {
            favorit_Item.setVisible(false);
            home_Item.setVisible(true);
            setTitle("Favorite List");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getBaseContext(),SettingsActivity.class));
            this.finish();
            return true;
        }
        else if (id == R.id.action_home)
        {
            if (!Static.checkInternet(getBaseContext()))
            {
                Static.favorite = true;
                Static.connectionStatus = false;
            }
            else
            {
                Static.favorite = false;
                Static.connectionStatus = true;
            }
            startActivity(new Intent(this,Home.class));
            this.finish();
        }
        else if(id == R.id.action_favorite)
        {
            Static.favorite = true;
            startActivity(new Intent(this,Home.class));
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
