package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity {

    Context ctx;
    MovieDBase movieDBase;
    Movie movie;
    EditText editTextSearch;
    Button ButtonSearch;
    ListView searchListView;
    ArrayList<Movie> movieArrayList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> searchedMovieArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        ctx = this;
        movieArrayList = new ArrayList<>();
        movieDBase = new MovieDBase(ctx);

        ButtonSearch = findViewById(R.id.button_searchMov);
        editTextSearch = findViewById(R.id.editText_searchBar);
        searchListView = findViewById(R.id.listView_search);

        //getting data from db as objects and adding them to an array
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

            movieArrayList.add(movie);


        }

        searchedMovieArray = new ArrayList<>();

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchedMovieArray.removeAll(searchedMovieArray);

                //getting searching text
                String searchText = editTextSearch.getText().toString().toLowerCase();

                //search that text inside the array to find a matching component
                for (Movie movies : movieArrayList) {
                    if (movies.getmTitle().toLowerCase().contains(searchText) || movies.getmDirector().toLowerCase().contains(searchText) || movies.getmActors().toLowerCase().contains(searchText)) {
                        String title = movies.getmTitle().toUpperCase();
                        searchedMovieArray.add(title);

                    }
                }
                // show nothing if there is no match
                if (searchedMovieArray.isEmpty()) {
                    System.out.println(searchedMovieArray);
                    Toast.makeText(getApplicationContext(), "No matching movie", Toast.LENGTH_SHORT).show();
                }

                //sending searchedMovieArray to the array adapter and showing it in the list view
                arrayAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, searchedMovieArray);
                searchListView.setAdapter(arrayAdapter);

            }
        });
    }
}