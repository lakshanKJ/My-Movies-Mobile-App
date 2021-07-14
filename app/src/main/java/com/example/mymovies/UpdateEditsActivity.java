package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class UpdateEditsActivity extends AppCompatActivity {

    Button buttonUpdate;
    EditText eTextTitle, eTextYear, eTextDirector, eTextActor, eTextRating, eTextReview;
    MovieDBase mDB;
    RatingBar starRateBar;
    CheckBox favouriteCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_edits);

        Context ctx = this;
        mDB = new MovieDBase(ctx);

        Intent intent = getIntent();
        int titleId = intent.getIntExtra("id", 0);
        Movie updateMovie = mDB.getSingleMovie(titleId);


        eTextTitle = findViewById(R.id.editUpTitle);
        eTextYear = findViewById(R.id.editUpYear);
        eTextDirector = findViewById(R.id.editUpDirector);
        eTextActor = findViewById(R.id.editUpActor);
        eTextRating = findViewById(R.id.editUpRating);
        eTextReview = findViewById(R.id.editUpReview);
        buttonUpdate = findViewById(R.id.button_update);
        starRateBar = findViewById(R.id.ratingBar_star);
        favouriteCheckbox = findViewById(R.id.UpCheckFavourite);


        //assign values to text boxes

        eTextTitle.setText(updateMovie.getmTitle());
        eTextYear.setText(updateMovie.getmYear() + "");
        eTextDirector.setText(updateMovie.getmDirector());
        eTextActor.setText(updateMovie.getmActors());
        eTextRating.setText(updateMovie.getmRating() + "");
        eTextReview.setText(updateMovie.getmReview());
        System.out.println(updateMovie.getFavourite());
        if (updateMovie.getFavourite()) {
            favouriteCheckbox.setChecked(true);
        }

        starRateBar.setRating(updateMovie.getmRating());


        //saving changes to the DB by clicking update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting updated values in the text boxes
                String upTitle = eTextTitle.getText().toString();
                int upYear = Integer.parseInt(eTextYear.getText().toString());
                String upDirector = eTextDirector.getText().toString();
                String upActors = eTextActor.getText().toString();
                int upRating = Integer.parseInt(eTextRating.getText().toString());
                String upReview = eTextReview.getText().toString();
                Boolean upFavourite = favouriteCheckbox.isChecked();

                Movie updateMovie = new Movie(titleId, upTitle, upYear, upDirector, upActors, upRating, upReview, upFavourite);
                //updating database
                Boolean done = mDB.updateSingleMovie(updateMovie);

                if (done == true) {
                    Toast.makeText(UpdateEditsActivity.this, "Movie Updated...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ctx, MainActivity.class));
                } else {
                    Toast.makeText(UpdateEditsActivity.this, "Movie Not Updated...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}