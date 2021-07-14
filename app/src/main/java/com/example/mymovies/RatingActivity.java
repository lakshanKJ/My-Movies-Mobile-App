package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RatingActivity extends AppCompatActivity {

    Button findIMDBRate;
    EditText searchIMDBMov;
    TextView title, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        findIMDBRate = findViewById(R.id.button_findIMDB);
        searchIMDBMov = findViewById(R.id.editText_searcbarIMDB);
        title = findViewById(R.id.textViewTitle);
        description = findViewById(R.id.textView_rateDesc);


        findIMDBRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = searchIMDBMov.getText().toString();
                new Thread(new RatingIMDB(movieTitle)).start();
            }
        });
    }

    class RatingIMDB implements Runnable {

        String searchedMovie;
        String movieID;

        RatingIMDB(String mName) {
            searchedMovie = mName;
        }

        @Override
        public void run() {

            StringBuilder stringB = new StringBuilder("");

            StringBuilder mTitleStringBuild = new StringBuilder("");

            try {
                // Make the connection
                URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_9ndhb7g4/" + searchedMovie.trim());
                URL urlRate = new URL("https://imdb-api.com/en/API/UserRatings/k_9ndhb7g4/" + movieID);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //getting input stream
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //test
                System.out.println("ATTENTION_______________________"+ bf.toString());


                //append lines to string builder
                String line;
                while ((line = bf.readLine()) != null) {
                    stringB.append(line);
                }

                //array - json
                JSONObject json = new JSONObject(stringB.toString());
                JSONArray jsonArray = json.getJSONArray("results");


                //getting the movie name, passing it to the string builder mTitleStringBuild
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String movie_Title = jsonObject.getString("title");

                    System.out.println("ATTENTION_______________________"+ movie_Title);

                    mTitleStringBuild.append(movie_Title + "\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }



            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //set all movies in text box
                    //test
                    System.out.println("ATTENTION_______________________"+ mTitleStringBuild.toString());

                    description.setText(mTitleStringBuild.toString());

                }
            });
        }

    }
}