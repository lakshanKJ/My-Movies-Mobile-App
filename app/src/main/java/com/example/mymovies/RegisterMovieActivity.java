package com.example.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMovieActivity extends AppCompatActivity {

    EditText titleData, directorData, yearData, actorsData, ratingData, reviewData;
    MovieDBase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

    }

    public void saveProcess(View view) {

        titleData = findViewById(R.id.editTextTitle);
        yearData = findViewById(R.id.editTextYear);
        directorData = findViewById(R.id.editTextDirector);
        actorsData = findViewById(R.id.editTextActors);
        ratingData = findViewById(R.id.editTextRating);
        reviewData = findViewById(R.id.editTextReview);

        //to get connection with db
        mDB = new MovieDBase(this);

        //initially no value
        String title = "";
        int year = 0;
        String director = "";
        String actors = "";
        int rating = 0;
        String review = "";

        try {
            //parsing values in the text boxes to the variables
            title = titleData.getText().toString();
            year = Integer.parseInt(yearData.getText().toString());
            director = directorData.getText().toString();
            actors = actorsData.getText().toString();
            rating = Integer.parseInt(ratingData.getText().toString());
            review = reviewData.getText().toString();

            Boolean done = false;

            //validation of year and rating , then passing data to the database
            if ((year < 2022 && year > 1895) && (rating <= 10 && rating > 0)) {
                Movie movie = new Movie(title, year, director, actors, rating, review, false);
                done = mDB.saveMovieData(movie);
                titleData.setText("");
                yearData.setText("");
                directorData.setText("");
                actorsData.setText("");
                ratingData.setText("");
                reviewData.setText("");
            } else {
                showAlert();
            }

            //showing the toast message
            if (done == true)
                Toast.makeText(RegisterMovieActivity.this, "Movie Added Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(RegisterMovieActivity.this, "Movie not added, Unsuccessful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            emptyAlert();
        }
    }

    //Alert - Empty text boxes
    public void emptyAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Empty details found");
        builder.setMessage("Check again");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dInterface, int id) {
                        dInterface.cancel();
                    }
                });

        AlertDialog aDialog = builder.create();
        aDialog.show();

    }

    //Showing the alert - year/rate wrong
    public void showAlert() {
        AlertDialog.Builder builderAlert = new AlertDialog.Builder(this);
        builderAlert.setTitle("Check your year and rating");
        builderAlert.setMessage("2022 > year > 1895 And 1 < Rating < 10");
        builderAlert.setCancelable(true);

        builderAlert.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dInterface, int id) {
                        dInterface.cancel();
                    }
                });

        AlertDialog aDialog = builderAlert.create();
        aDialog.show();

    }
}