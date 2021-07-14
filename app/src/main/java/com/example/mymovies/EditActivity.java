package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    MovieDBase dBase;
    ListView lView;
    ArrayAdapter adapter;
    ArrayList<Movie> listComponents;
    ArrayList<String> titleComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dBase = new MovieDBase(this);

        //store details of movies as objects
        listComponents = new ArrayList<>();

        //store titles of movies
        titleComponents = new ArrayList<>();

        lView = findViewById(R.id.editMovieList);

        //read db , and add data to array
        Cursor cur = dBase.getAllData();
        while (cur.moveToNext()) {
            Movie movie = new Movie(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getInt(5),
                    cur.getString(6),
                    cur.getInt(7) == 1);

            String movieTitle = movie.getmTitle();
            listComponents.add(movie);
            titleComponents.add(movieTitle);
        }
        //send titles as an array to the array adapter and displaying titles in list view
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleComponents);
        lView.setAdapter(adapter);

        //on list view item click
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //clicking on the title on the listView will go to the next activity
                String listViewItem = lView.getItemAtPosition(position).toString();
                Movie movieObj = listComponents.get(position);

                Toast.makeText(getApplicationContext(), listViewItem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditActivity.this, UpdateEditsActivity.class);
                intent.putExtra("id", movieObj.getmId());
                startActivity(intent);
            }
        });

    }
}