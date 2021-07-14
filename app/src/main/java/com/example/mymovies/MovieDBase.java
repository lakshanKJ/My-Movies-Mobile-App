package com.example.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class MovieDBase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_Name = "MyMoviesDB.db";
    private static final String TABLE_Name = "MoviesTable";

    //table columns
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_YEAR = "year";
    private static final String MOVIE_DIRECTOR = "director";
    private static final String MOVIE_ACTORS = "actors";
    private static final String MOVIE_FAVOURITE = "favourite";
    private static final String MOVIE_RATING = "rating";
    private static final String MOVIE_REVIEW = "review";

    public MovieDBase(Context context) {
        super(context, DB_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating the table
        String createTable = " create Table " + TABLE_Name + " ("
                + MOVIE_ID + " INTEGER primary key Autoincrement, "
                + MOVIE_TITLE + " TEXT  , "
                + MOVIE_YEAR + " TEXT, "
                + MOVIE_DIRECTOR + " TEXT, "
                + MOVIE_ACTORS + " TEXT, "
                + MOVIE_RATING + " TEXT, "
                + MOVIE_REVIEW + " TEXT , "
                + MOVIE_FAVOURITE + " TEXT " +
                ");";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //update on the table
        db.execSQL("drop Table if exists " + TABLE_Name);
//        onCreate(db);
    }

    public Boolean saveMovieData(Movie movie) {

        //saving data inside the table
        SQLiteDatabase SQLDBase = this.getWritableDatabase();
        ContentValues contValues = new ContentValues();

        //putting contentValues to save new data
        contValues.put(MOVIE_TITLE, movie.getmTitle());
        contValues.put(MOVIE_YEAR, movie.getmYear());
        contValues.put(MOVIE_DIRECTOR, movie.getmDirector());
        contValues.put(MOVIE_ACTORS, movie.getmActors());
        contValues.put(MOVIE_RATING, movie.getmRating());
        contValues.put(MOVIE_REVIEW, movie.getmReview());
        contValues.put(MOVIE_FAVOURITE, movie.getFavourite());

        long resultValue = SQLDBase.insert(TABLE_Name, null, contValues);

        if (resultValue == -1) {
            return false;

        } else {
            return true;
        }
    }

    //view data in the DB using cursor
    public Cursor getAllData() {
        SQLiteDatabase SQLDBase = this.getReadableDatabase();
        String queryValue = " Select * from " + TABLE_Name;
        Cursor cur = SQLDBase.rawQuery(queryValue, null);
        return cur;
    }

    //favourite update in DB - column
    public Boolean updateFavourite(String title, int year, String director, String actor, int rating, String review, Boolean favourite) {
        SQLiteDatabase SQLDBase = this.getWritableDatabase();
        ContentValues contValues = new ContentValues();

        //putting new contentValues to update entry
        contValues.put(MOVIE_TITLE, title);
        contValues.put(MOVIE_YEAR, year);
        contValues.put(MOVIE_DIRECTOR, director);
        contValues.put(MOVIE_ACTORS, actor);
        contValues.put(MOVIE_RATING, rating);
        contValues.put(MOVIE_REVIEW, review);
        contValues.put(MOVIE_FAVOURITE, favourite);

        SQLDBase.update(TABLE_Name, contValues, "title = ?", new String[]{title});
        return true;
    }


    public ArrayList<Movie> getEveryMovie() {

        //saving all movies in the db to an array, then return

        ArrayList<Movie> movieArrayList = new ArrayList<>();
        SQLiteDatabase SQLDBase = getReadableDatabase();
        String queryValue = " Select * from " + TABLE_Name;
        Cursor cur = SQLDBase.rawQuery(queryValue, null);


        if (cur.moveToFirst()) {
            do {
                Movie movie = new Movie();

                //taking row details
                movie.setmTitle(cur.getString(1));
                movie.setmYear(cur.getInt(2));
                movie.setmDirector(cur.getString(3));
                movie.setmActors(cur.getString(4));
                movie.setmRating(cur.getInt(5));
                movie.setmReview(cur.getString(6));
                movie.setFavourite(cur.getWantsAllOnMoveCalls());

                //adding objects to an array list
                movieArrayList.add(movie);

            } while (cur.moveToNext());

        }
        return movieArrayList;
    }

    //read movie from DB when ID is known and returning it
    public Movie getSingleMovie(int id) {

        SQLiteDatabase SQLDBase = getWritableDatabase();

        Cursor cur = SQLDBase.query(TABLE_Name, new String[]{MOVIE_ID, MOVIE_TITLE, MOVIE_YEAR, MOVIE_DIRECTOR, MOVIE_ACTORS, MOVIE_RATING, MOVIE_REVIEW, MOVIE_FAVOURITE},
                "id =?", new String[]{String.valueOf(id)}
                , null, null, null);

        Movie movie;
        if (cur != null) {
            cur.moveToFirst();
            // getting data from rows
            movie = new Movie(
                    cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getInt(5),
                    cur.getString(6),
                    cur.getInt(7) == 1

            );
            return movie;
        }
        return null;
    }

    // single movie update
    public Boolean updateSingleMovie(Movie uPMovie) {

        SQLiteDatabase SQLDBase = getWritableDatabase();
        ContentValues contValues = new ContentValues();

        //putting updated contentValues
        contValues.put(MOVIE_TITLE, uPMovie.getmTitle());
        contValues.put(MOVIE_YEAR, uPMovie.getmYear());
        contValues.put(MOVIE_DIRECTOR, uPMovie.getmDirector());
        contValues.put(MOVIE_ACTORS, uPMovie.getmActors());
        contValues.put(MOVIE_RATING, uPMovie.getmRating());
        contValues.put(MOVIE_REVIEW, uPMovie.getmReview());
        contValues.put(MOVIE_FAVOURITE, uPMovie.getFavourite());


        Cursor cur = SQLDBase.rawQuery(" SELECT * from " + TABLE_Name + " where id =?", new String[]{String.valueOf(uPMovie.getmId())});

        if (cur.getCount() > 0) {
            long resultValue = SQLDBase.update(TABLE_Name, contValues, "id =?", new String[]{String.valueOf(uPMovie.getmId())});

            if (resultValue == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }

    //delete rows from table in database
    public void delete(String movieName) {
        SQLiteDatabase SQLDBase = this.getWritableDatabase();
        SQLDBase.execSQL("delete from " + TABLE_Name + " where title = '" + movieName + "' ");
    }
}
