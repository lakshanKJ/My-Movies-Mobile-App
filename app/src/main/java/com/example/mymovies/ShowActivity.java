package com.example.mymovies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowActivity extends AppCompatActivity {

    Button saveFavouriteBtn;
    TextView titleTextView;
    MyMovieAdapter myMovieAdapter = null;
    Movie movie;
    ListView lView;
    CheckBox favouriteCheckbox;
    ArrayList<Movie> movieArrayList;
    MovieDBase movieDBase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Context ctx = this;

        movieArrayList = new ArrayList<>();
        movieDBase = new MovieDBase(ctx);

        lView = findViewById(R.id.movieList_show);
        titleTextView = findViewById(R.id.textView_mTitle);
        favouriteCheckbox = findViewById(R.id.checkBox_fav);
        saveFavouriteBtn = findViewById(R.id.fav_btn_show);


        //calling method - add as favourite
        checkBoxValuesSave();

        //read values in db and creating an object
        Cursor cur = movieDBase.getAllData();
        while (cur.moveToNext()) {

            movie = new Movie(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getInt(5),
                    cur.getString(6),
                    cur.getInt(7) == 1);

            //adding movie details as objects to an array
            movieArrayList.add(movie);

        }

        //showing movies in list view using myMovieAdapter(array adapter)

        movieArrayList = movieDBase.getEveryMovie();
        System.out.println("ATTENTION_______ " + movieArrayList);

        //ordering
        Comparator comparator = Comparator.comparing(Movie::getmTitle);
        Collections.sort(movieArrayList, comparator);

        myMovieAdapter = new MyMovieAdapter(ctx, R.layout.favourite_view, movieArrayList);
        lView.setAdapter(myMovieAdapter);
    }

    //save checked favourites in DB
    public void checkBoxValuesSave() {

        saveFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer stringBuffer = new StringBuffer();
                ArrayList<Movie> movieArray = myMovieAdapter.movieArray;
                ArrayList<String> movieTitleArray = new ArrayList<>();

                stringBuffer.append("");

                for (int i = 0; i < movieArray.size(); i++) {
                    Movie movieB = movieArray.get(i);

                    if (movieB.getFavourite()) {
                        //showing on a string buffer - favourite movie title
                        stringBuffer.append("" + movieB.getmTitle());

                        //add favorite movie title to an array
                        movieTitleArray.add(movieB.getmTitle());
                    }
                }

                System.out.println(movieTitleArray.toString());


                //get data from db and adding them to an array list
                Cursor cur = movieDBase.getAllData();
                System.out.println(cur);

                while (cur.moveToNext()) {
                    for (int i = 0; i < movieTitleArray.size(); i++) {
                        if (cur.getString(1).equals(movieTitleArray.get(i))) {

                            String title = cur.getString(1);
                            int year = cur.getInt(2);
                            String director = cur.getString(3);
                            String actor = cur.getString(4);
                            int rating = cur.getInt(5);
                            String review = cur.getString(6);

                            boolean done = movieDBase.updateFavourite(title, year, director, actor, rating, review, true);

                            if (done) {
                                Toast.makeText(getApplicationContext(), stringBuffer, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });

    }
}