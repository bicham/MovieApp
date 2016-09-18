package com.example.ahmed.movieapp.Models.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ahmed.movieapp.Models.Data;
import com.example.ahmed.movieapp.Models.DataList;
import com.example.ahmed.movieapp.Models.ReviewData;
import com.example.ahmed.movieapp.Models.ReviewDataList;
import com.example.ahmed.movieapp.Models.TrailersData;
import com.example.ahmed.movieapp.Models.TrailersDataList;

/**
 * Created by Ahmed on 9/16/2016.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MovieApp.db";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creation of table MovieData

        String query = "CREATE TABLE "+ MovieDataContract.TABLE_MOVIE_DATA + " ( "
                + MovieDataContract.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + MovieDataContract.COLUMN_Poster_Path + " TEXT, "
                + MovieDataContract.COLUMN_Overview + " TEXT, "
                + MovieDataContract.COLUMN_Release_Data + " TEXT, "
                + MovieDataContract.COLUMN_Title + " TEXT, "
                + MovieDataContract.COLUMN_Vote_Average + " TEXT, "
                + MovieDataContract.COLUMN_Backdrop_Path + " TEXT, "
                + MovieDataContract.COLUMN_Vote_Count + " INTEGER, "
                + MovieDataContract.COLUMN_Original_Title + " TEXT "
                +");";

        db.execSQL(query);

        // Creation of table MovieVideo

        query = "CREATE TABLE "+ MovieVideoContract.TABLE_MOVIE_VIDEO + " ( "
                + MovieVideoContract.COLUMN_ID + " TEXT PRIMARY KEY, "
                + MovieVideoContract.COLUMN_MOVIE_ID + " INTEGER, "
                + MovieVideoContract.COLUMN_KEY + " TEXT, "
                + MovieVideoContract.COLUMN_NAME + " TEXT, "
                + MovieVideoContract.COLUMN_SITE + " TEXT, "
                + MovieVideoContract.COLUMN_SIZE + " INTEGER, "
                + MovieVideoContract.COLUMN_TYPE + " TEXT, "
                + "FOREIGN KEY("+ MovieVideoContract.COLUMN_MOVIE_ID +") REFERENCES " + MovieDataContract.TABLE_MOVIE_DATA +" ("+MovieDataContract.COLUMN_ID+")"
                + " );";

        db.execSQL(query);

        //Creation of table MovieReview

        query = "CREATE TABLE "+ MovieReviewContract.TABLE_MOVIE_REVIEW + " ( "
                + MovieReviewContract.COLUMN_ID + " TEXT PRIMARY KEY, "
                + MovieReviewContract.COLUMN_MOVIE_ID + " INTEGER, "
                + MovieReviewContract.COLUMN_URL + " TEXT, "
                + MovieReviewContract.COLUMN_AUTHOR + " TEXT, "
                + "FOREIGN KEY("+ MovieReviewContract.COLUMN_MOVIE_ID +") REFERENCES " + MovieDataContract.TABLE_MOVIE_DATA +" ("+MovieDataContract.COLUMN_ID+")"
                + " );";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean Insert(Data data, TrailersDataList trailersData, ReviewDataList reviewData)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dataValues = new ContentValues();

        Boolean insertion = false;




        //values of MovieData table



        dataValues.put(MovieDataContract.COLUMN_ID,data.id);
        dataValues.put(MovieDataContract.COLUMN_Poster_Path,data.poster_path);
        dataValues.put(MovieDataContract.COLUMN_Overview,data.overview);
        dataValues.put(MovieDataContract.COLUMN_Release_Data,data.release_date);
        dataValues.put(MovieDataContract.COLUMN_Title,data.title);
        dataValues.put(MovieDataContract.COLUMN_Vote_Average,String.valueOf(data.vote_average));
        dataValues.put(MovieDataContract.COLUMN_Backdrop_Path,data.backdrop_path);
        dataValues.put(MovieDataContract.COLUMN_Vote_Count,data.vote_count);
        dataValues.put(MovieDataContract.COLUMN_Original_Title,data.original_title);

        try {

            db.insert(MovieDataContract.TABLE_MOVIE_DATA,null,dataValues);



            //values of MovieVideo table and insertion

            for (int i=0 ;i<trailersData.results.size();i++)
            {
                ContentValues trailersValues = new ContentValues();

                trailersValues.put(MovieVideoContract.COLUMN_ID,trailersData.results.get(i).id);
                trailersValues.put(MovieVideoContract.COLUMN_MOVIE_ID,data.id);
                trailersValues.put(MovieVideoContract.COLUMN_KEY,trailersData.results.get(i).key);
                trailersValues.put(MovieVideoContract.COLUMN_NAME,trailersData.results.get(i).name);
                trailersValues.put(MovieVideoContract.COLUMN_SITE,trailersData.results.get(i).site);
                trailersValues.put(MovieVideoContract.COLUMN_SIZE,trailersData.results.get(i).size);
                trailersValues.put(MovieVideoContract.COLUMN_TYPE,trailersData.results.get(i).type);

                db.insert(MovieVideoContract.TABLE_MOVIE_VIDEO,null,trailersValues);
            }




            //values of MovieReview and insertion

            for (int j =0 ; j<reviewData.results.size() ; j++)
            {

                ContentValues ReviewsValues = new ContentValues();

                ReviewsValues.put(MovieReviewContract.COLUMN_ID,reviewData.results.get(j).id);
                ReviewsValues.put(MovieReviewContract.COLUMN_MOVIE_ID,data.id);
                ReviewsValues.put(MovieReviewContract.COLUMN_URL,reviewData.results.get(j).url);
                ReviewsValues.put(MovieReviewContract.COLUMN_AUTHOR,reviewData.results.get(j).author);

                db.insert(MovieReviewContract.TABLE_MOVIE_REVIEW,null,ReviewsValues);

            }

            insertion = true;

        }
        catch (Exception e)
        {
            insertion = false;

        }


        db.close();


        return insertion;


    }

    public DataList getAllMovies()
    {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + MovieDataContract.TABLE_MOVIE_DATA + " WHERE 1";

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        DataList allData = new DataList();

        while (!cursor.isAfterLast())
        {
            Data movieObject = new Data();

            movieObject.id = cursor.getInt(cursor.getColumnIndex(MovieDataContract.COLUMN_ID));
            movieObject.poster_path = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Poster_Path));
            movieObject.overview = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Overview));
            movieObject.release_date = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Release_Data));
            movieObject.title = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Title));

            movieObject.vote_average =
                    Float.parseFloat(cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Vote_Average)));

            movieObject.backdrop_path = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Backdrop_Path));
            movieObject.vote_count = cursor.getInt(cursor.getColumnIndex(MovieDataContract.COLUMN_Vote_Count));
            movieObject.original_title = cursor.getString(cursor.getColumnIndex(MovieDataContract.COLUMN_Original_Title));


            allData.results.add(movieObject);

            cursor.moveToNext();

        }


        return allData;

    }

    public TrailersDataList getMovieTrailers(int movieID)
    {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + MovieVideoContract.TABLE_MOVIE_VIDEO
                + " WHERE " + MovieVideoContract.COLUMN_MOVIE_ID + " = "
                + movieID;


        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        TrailersDataList allTrailers = new TrailersDataList();

        while (!cursor.isAfterLast())
        {
            TrailersData trailersData = new TrailersData();

            trailersData.id = cursor.getString(cursor.getColumnIndex(MovieVideoContract.COLUMN_ID));
            trailersData.key = cursor.getString(cursor.getColumnIndex(MovieVideoContract.COLUMN_KEY));
            trailersData.name = cursor.getString(cursor.getColumnIndex(MovieVideoContract.COLUMN_NAME));
            trailersData.site = cursor.getString(cursor.getColumnIndex(MovieVideoContract.COLUMN_SITE));
            trailersData.size = cursor.getInt(cursor.getColumnIndex(MovieVideoContract.COLUMN_SIZE));
            trailersData.type = cursor.getString(cursor.getColumnIndex(MovieVideoContract.COLUMN_TYPE));

            allTrailers.results.add(trailersData);

            cursor.moveToNext();

        }


        db.close();

        return allTrailers;


    }

    public ReviewDataList getReviewsData(int movieID)
    {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + MovieReviewContract.TABLE_MOVIE_REVIEW
                + " WHERE " + MovieReviewContract.COLUMN_MOVIE_ID + " = "
                + movieID;


        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        ReviewDataList allReviews = new ReviewDataList();

        while (!cursor.isAfterLast())
        {
            ReviewData reviewObject = new ReviewData();

            reviewObject.id = cursor.getString(cursor.getColumnIndex(MovieReviewContract.COLUMN_ID));
            reviewObject.url = cursor.getString(cursor.getColumnIndex(MovieReviewContract.COLUMN_URL));
            reviewObject.author = cursor.getString(cursor.getColumnIndex(MovieReviewContract.COLUMN_AUTHOR));

            allReviews.results.add(reviewObject);


            cursor.moveToNext();

        }
        allReviews.total_results = allReviews.results.size();

        db.close();

        return allReviews;

    }

    public void deleteall()
    {
        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM " + MovieDataContract.TABLE_MOVIE_DATA ;

        db.execSQL(query);

        query = "DELETE FROM " + MovieReviewContract.TABLE_MOVIE_REVIEW ;

        db.execSQL(query);

        query = "DELETE FROM " + MovieVideoContract.TABLE_MOVIE_VIDEO ;

        db.execSQL(query);

        db.close();
    }

}
