package com.example.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyMovieAdapter extends ArrayAdapter<Movie> {


    ArrayList<Movie> movieArray;
    private Context ctx;
    private int resourceID;

    MyMovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);
        this.ctx = context;
        this.resourceID = resource;
        this.movieArray = movies;
    }

    private class ListViewItem {

        CheckBox checkBoxFav;
        TextView movieTitleTV;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View viewC = convertView;
        ListViewItem vItem = new ListViewItem();

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewC = layoutInflater.inflate(R.layout.favourite_view, null);

            vItem.movieTitleTV = (TextView) viewC.findViewById(R.id.textView_mTitle);
            vItem.checkBoxFav = (CheckBox) viewC.findViewById(R.id.checkBox_fav);


            //setting favourites according to the check box
            vItem.checkBoxFav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox checkB = (CheckBox) v;
                    Movie movieObj = (Movie) checkB.getTag();
                    Toast.makeText(getContext(), "favourite changed ", Toast.LENGTH_LONG).show();
                    movieObj.setFavourite(checkB.isChecked());

                }
            });

        } else {

            vItem = (ListViewItem) viewC.getTag();
        }

        try {

            //displaying image, movie title and checkbox inside list view
            Movie movieB = movieArray.get(position);
            vItem.movieTitleTV.setText(movieB.getmTitle());
            vItem.checkBoxFav.setChecked(movieB.getFavourite());
            vItem.checkBoxFav.setTag(movieB);

        } catch (NullPointerException e) {

            System.out.println("error detected");
        }


        return viewC;
    }

}
