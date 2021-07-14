package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //register movie button clickOn
    public void registerMovieClick(View view) {
        Intent reg_movie = new Intent(MainActivity.this, RegisterMovieActivity.class);
        startActivity(reg_movie);
    }

    //display movies button clickOn
    public void displayMoviesClick(View view) {
        Intent dis_movie = new Intent(MainActivity.this, ShowActivity.class);
        startActivity(dis_movie);
    }

    //favourites button clickOn
    public void favouritesClick(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayFavouritesActivity.class);
        startActivity(intent);
    }

    //edit movies button clickOn
    public void editClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }

    //search button clickOn
    public void searchClick(View view) {
        Intent intent = new Intent(MainActivity.this, SearchMovieActivity.class);
        startActivity(intent);
    }

    //rating button clickOn
    public void ratingClick(View view) {
        Intent intent = new Intent(MainActivity.this, RatingActivity.class);
        startActivity(intent);
    }
}