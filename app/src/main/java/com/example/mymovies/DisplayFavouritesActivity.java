package com.example.mymovies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DisplayFavouritesActivity extends AppCompatActivity {

    MovieDBase db;
    ArrayList<Movie> movieArrayList;
    Movie movieObj;
    ListView favouriteListView;
    MyMovieAdapter myMovieAdapter = null;
    Context ctx;
    Button saveFavouritesBtn;
    ArrayList<Movie> trueFavouritesArray;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_favourites);

        process();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void process() {
        ctx = this;
        movieArrayList = new ArrayList<>();
        db = new MovieDBase(this);
        saveFavouritesBtn = findViewById(R.id.button_saveFav);
        favouriteListView = findViewById(R.id.movieList_show);

        //creating an object by reading the data inside the db
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            movieObj = new Movie(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7) == 1);

            //passing the object to the arrayList
            movieArrayList.add(movieObj);
        }

        //get true favourites from the movieArrayList and adding them to the
        //trueFavouritesArray
        trueFavouritesArray = new ArrayList<>();
        for (Movie mov : movieArrayList) {
            if (mov.getFavourite()) {
                trueFavouritesArray.add(mov);
            }
        }

        //adding favourite movies' titles to the list view using array adapter
        myMovieAdapter = new MyMovieAdapter(ctx, R.layout.favourite_view, trueFavouritesArray);
        Comparator comp = Comparator.comparing(Movie::getmTitle);
        Collections.sort(trueFavouritesArray, comp);
        favouriteListView.setAdapter(myMovieAdapter);

        saveBtnProcess();
    }

    public void saveBtnProcess() {

        saveFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer stringBuffer = new StringBuffer();
                ArrayList<String> favouriteTitleArray = new ArrayList<>();
                int listLength = movieArrayList.size();

                for (int i = 0; i < listLength; i++) {
                    Movie movieB = movieArrayList.get(i);

                    if (!movieB.getFavourite()) {
                        stringBuffer.append("" + movieB.getmTitle());
                        favouriteTitleArray.add(movieB.getmTitle());
                    }
                }

                //adding changed favourites to the DB
                Cursor cur = db.getAllData();
                while (cur.moveToNext()) {
                    for (int i = 0; i < favouriteTitleArray.size(); i++) {
                        if (cur.getString(1).equals(favouriteTitleArray.get(i))) {
                            String title = cur.getString(1);
                            int year = cur.getInt(2);
                            String director = cur.getString(3);
                            String actor = cur.getString(4);
                            int rating = cur.getInt(5);
                            String review = cur.getString(6);

                            boolean done = db.updateFavourite(title, year, director, actor, rating, review, false);

                            if (done == true) {
                                Toast.makeText(getApplicationContext(), stringBuffer, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

    }
}